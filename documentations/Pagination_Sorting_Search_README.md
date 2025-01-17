# Search, Pagination, and Sorting

## Search

Users on e-commerce websites often search by product names, description, or maybe some other attribute of a product, and they expect the relevant search results to be shown. How does this work?

What we can easily observe is that, when we search using some query on let's say Flipkart, we see a `GET` call being made which looks something like this:

`https://www.flipkart.com/s?k=pixel`

While other websites like CureFit make a `POST` call for the same? Which one to use?

Typically when we search, we pass query-params and filters to the backend. Again, this information can be sent as query params in a `GET` call, but that would make the URL very big:

`https://www.flipkart.com/s?k=pixel&sortOrder=price&color=black&memory=256gb`

Based on the browser, this supported length could vary. Hence, we can pass this information in the Body, and we'll need to use a `POST` call. We can pass this body in the `GET` operation as well, but it's not a usual thing to do, and things like RestTemplate doesn't even support sending body in `GET` calls.

### Pros & Cons of `POST` vs `GET`

| POST | GET |
| - | - |
| Data present in the body as well | Sharable URL |
| Body isn't cached | Caching is optimized (URL cached) |
| No size limit | URL limit by browser |
| Easier to send the filter and order data | Less readable, and difficult to format |

## Pagination

Now when we search on websites, there could be 100s of search results, but we only want to see a small subset of it, maybe 10 products per page. Sometimes, the entire search result is too large to be sent in 1 call, and needs to be split. This is possible using Pagination.

This works by adding another query param to the request URL. Example:

`https://www.flipkart.com/s?k=pixel&sortOrder=price&color=black&memory=256gb&pageNum=1`

This request can also include a page size.

## Sorting Order

Similarly, we can pass the sorting params to the request.

## Pagination and Sorting using JPA

JPA makes this work easier for us to implement server-side Pagination and Sorting.

```java
public Page<Product> getAllProducts(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Product> productsPage = productRepository.findAll(pageable);
    if (productsPage.isEmpty()) {
        throw new ProductNotFoundException("No products found");
    }
    return productsPage;
}
```

Response:

```json
{
    "content": [
        {
            "id": 3,
            "title": "Sample Product",
            "description": "This is a sample product description.",
            "price": 100,
            "category": {
                "id": 2,
                "name": "Sample Category"
            }
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 1,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": false,
    "totalElements": 5,
    "totalPages": 5,
    "size": 1,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
```

## Elastic Search

Elastic Search makes use of Inverted Index to support:
- Fast Retrieval of data
- Persistence of data
- Filtering
- Sorting

> How does it work?

1. When a product is saved/deleted/updated, the same operation is done to the `Database + Elastic Search`
2. When we retrieve a product, we retrieve only from Elastic Search
    - This means that `PUT`, `POST`, `DELETE` are performed to both databases
    - `GET` is done from Elastic Search
3. This can lead to a large amount of data being stored to Elastic Search (imagine the number of products on Amazon, a Billion). Hence we take a hybrid approach and store only that data in ES that we need to show in the search results. Further information can be fetched from the database.