API Design
==========

Full Hypermedia REST API.

Get single resource
---

#### Simple
At a minimum, the response contains:
* Type and ID of the resource
* Resource payload data
* A self link. Other links are possible if needed

Note that the Version should be included as the ETag in the headers, and the Updated Date should be included as Last-Modified in the headers.

```json
{
  "type": "resource",
  "id": "abc",
  "data": {
    "name": "Graham",
    "age": "34"
  },
  "links": {
    "self": {
      "href": "http://localhost:8080/api/resource/graham"
    },
    "avatar": {
      "href": "http://localhost:8080/api/resource/graham/avatar",
      "type": "image/png"
    }
  }
}
```

#### Relationships
Resources can also indicate that they are related to other, pre-existing resources, and these relationships can include some data if desired. This data is on the relationship link and not on the related resource. Relationships contain exactly:
* Type and ID of the resource
* Relationship payload data, if present
* A self link for the related resource

```json
{
  "type": "resource",
  "id": "abc",
  "data": {
    "name": "Graham",
    "age": "34"
  },
  "links": {
    "self": {
      "href": "http://localhost:8080/api/resource/graham"
    }
  },
  "related": [{
      "type": "other",
      "id": "theOther",
      "links": {
        "self": {
          "href": "http://localhost:8080/api/other/theOther"
        }
      }
  }, {
      "type": "third",
      "id": "again",
      "data": {
        "answer": 42
      },
      "links": {
        "self": {
          "href": "http://localhost:8080/api/third/again"
        }
      }
  }]
}
```

#### Relationships + Included Resource
When including a relationship to another resource, it is possible to include some details of the related resource as well. This is done in a separate section so that if the same resource is linked to multiple times, it is only included in the document once. Included resources are keyed by the self link from the relationship, and contain the Type, ID and appropriate resource payload data. Versioning information is *not* included, so that it is not possible to use this version of the resource to mutate it. The resource payload data does not need to contain everything that the full resource would, and the structure need not be the same but ideally it should for consistency.

```json
{
  "type": "resource",
  "id": "abc",
  "data": {
    "name": "Graham",
    "age": "34"
  },
  "links": {
    "self": {
      "href": "http://localhost:8080/api/resource/graham"
    }
  },
  "related": [{
      "type": "other",
      "id": "theOther",
      "links": {
        "self": {
          "href": "http://localhost:8080/api/other/theOther"
        }
      }
  }, {
      "type": "third",
      "id": "again",
      "data": {
        "answer": 42
      },
      "links": {
        "self": {
          "href": "http://localhost:8080/api/third/again"
        }
      }
  }],
  "included": {
    "http://localhost:8080/api/other/theOther": {
      "type": "other",
      "id": "theOther",
      "data": {
        "name": "I am the other"
      }
    },
    "http://localhost:8080/api/third/again": {
      "type": "third",
      "id": "again",
      "data": {
        "name": "I am the third resource"
      }
    }
  }
}
```

List
----
Retrieving a List of resources is virtually the same as above, but with an additional layer that includes pagination details.

In this case, relationships are included on the individual resources but the included version of related resurces are included at the topmost level. This allows for sharing of included resources between multiple resources.

```json
{
  "pagination": {
    "total": 53,
    "offset": 27
  },
  "data": [{
    "type": "resource",
    "id": "abc",
    "data": {
      "name": "Graham",
      "age": "34"
    },
    "links": {
      "self": {
        "href": "http://localhost:8080/api/resource/graham"
      },
      "avatar": {
        "href": "http://localhost:8080/api/resource/graham/avatar",
        "type": "image/png"
      }
    },
    "related": [{
        "type": "other",
        "id": "theOther",
        "links": {
          "self": {
            "href": "http://localhost:8080/api/other/theOther"
          }
        }
    }, {
        "type": "third",
        "id": "again",
        "data": {
          "answer": 42
        },
        "links": {
          "self": {
            "href": "http://localhost:8080/api/third/again"
          }
        }
    }]
  }],
  "links": {
    "self": {
      "href": "http://localhost:8080/api/resource?offset=27"
    },
    "first": {
      "href": "http://localhost:8080/api/resource"
    },
    "prev": {
      "href": "http://localhost:8080/api/resource?offset=26"
    },
    "next": {
      "href": "http://localhost:8080/api/resource?offset=28"
    },
    "last": {
      "href": "http://localhost:8080/api/resource?offset=52"
    }
  },
  "included": {
    "http://localhost:8080/api/other/theOther": {
      "type": "other",
      "id": "theOther",
      "data": {
        "name": "I am the other"
      }
    },
    "http://localhost:8080/api/third/again": {
      "type": "third",
      "id": "again",
      "data": {
        "name": "I am the third resource"
      }
    }
  }
}
```
Create
------
Creating a resource is performed by sending an HTTP POST to the list url, with the data to create. ID, Links and Included Resources should not be included in the request payload. Relationships should be, but the link to the related resource is optional.

```json
{
  "type": "resource",
  "data": {
    "name": "Graham",
    "age": 34
  },
  "related": [{
    "type": "other",
    "id": "theOther"
  }, {
    "type": "third",
    "id": "again",
    "data": {
      "answer": 42
    }
  }]
}
```

A Create will result in an HTTP 201 with the new resource on success, including updated ETag and Last-Modified headers, or an appropriate HTTP 4xx on failure. A success will also include a Location header with the Self Link of the newly created resource.

Edit
----
Updating a resource is performed by sending an HTTP PUT to the resource self link, with the data to update. The format of this data is exactly the same as when creating it. The PUT request should contain an If-Match header that contains the ETag from the requested resource, to ensure that the resource hasn't changed since it was last retrieved.

```json
{
  "type": "resource",
  "data": {
    "name": "Graham",
    "age": 34
  },
  "related": [{
    "type": "other",
    "id": "theOther"
  }, {
    "type": "third",
    "id": "again",
    "data": {
      "answer": 42
    }
  }]
}
```

An Update will result in an HTTP 200 with the new resource on success, including updated ETag and Last-Modified headers, or an appropriate HTTP 4xx on failure.

Delete
------
Deleting is simply an HTTP DELETE sent to the Self link of the resource. The DELETE request should contain an If-Match header that contains the ETag from the requested resource, to ensure that the resource hasn't changed since it was last retrieved.

A Delete will result in an HTTP 204 with no body on success, or an appropriate HTTP 4xx on failure.

Error response
--------------
Errors are always returned with the same representation. This includes using the HTTP Status Code as is appropriate, a richer error code in the body, and a human readable description. Some errors will include additional data in the error payload as well.

### Simple Error
401 Unauthorized

```json
{
  "error": "UNAUTHORIZED",
  "description": "You are not authorized to delete this resource"
}
```

### Error with data
400 Bad Request

```json
{
  "error": "INVALID_REQUEST",
  "description": "The request contained invalid field values",
  "data": {
    "fields": [
      "foo",
      "bar",
      "baz"
    ]
  }
}
```
