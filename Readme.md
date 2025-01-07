# Blog App

A Simple Blog App that allows users to create and manage blogs.

## Features

- Create, update, and delete blogs
- Get all blogs
- Get a blog by id
- Get all authors
- Get an author by id
- Register a new user
- Login a user


## Technologies

- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- JWT
- Lombok


## Installation

1. Clone the repository
2. Run `mvn clean package`
3. Run `java -jar target/blog-app-0.0.1-SNAPSHOT.jar`


# **API Endpoints**


# Authentication

### POST /api/auth/register
Register a new user 

```bash
curl -X POST http://localhost:8080/api/auth/register
    -H "Content-Type: application/json"
    -d '{
        "name": "user1",
        "email": "t1@t.com",
        "password": "password"
    }'
``` 

#### Response Status Codes: 201
| Code | Description | Response Body |
| --- | --- | --- |
| 201 | Created | The user was successfully created |
| id | String | Unique identifier for the user |
| name | String | The name of the user |
| email | String | The email of the user |
| imageUrl | String | The URL of the user's profile image |

#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |

### POST /api/auth/login
Login a user with email and password

```bash
curl -X POST http://localhost:8080/api/auth/login
    -H "Content-Type: application/json"
    -d '{
        "email": "t1@t.com",
        "password": "password"
    }'
``` 

#### Response Status Codes: 200
| Code | Description | Response Body |
| --- | --- | --- |
| token | String | The access token |

#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |


### Get /api/auth/user
Get the current user

```bash
curl -X GET http://localhost:8080/api/auth/user
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

#### Response Status Codes: 200
| Code | Description | Response Body |
| --- | --- | --- |
| 200 | OK | The user was successfully retrieved |
| id | String | Unique identifier for the user |
| name | String | The name of the user |
| email | String | The email of the user |
| imageUrl | String | The URL of the user's profile image |

#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |

### Put /api/auth/user/profile
Update the current user's profile

```bash
curl -X PUT http://localhost:8080/api/auth/user/profile
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
    -d '{
        "name": "user1 updated",
        "email": "t1@t.com",
        "password": "password"
    }'
```

#### Response Status Codes: 200
| Code | Description | Response Body |
| --- | --- | --- |
| 200 | OK | The user was successfully updated |
| status | Integer | The status code |
| message | String | The message |


#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| status | Integer | The status code |
| message | String | The error message |
    




# Blogs
### GET /api/blogs
Get All blogs 

```bash
curl -X GET http://localhost:8080/api/blogs
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

Response 

```json
[
    {
        "id": "b1",
        "title": "blog1",
        "content": "blog1 content",
        "author": {
            "id": "u1",
            "name": "user1",
            "email": "t1@t.com",
            "imageUrl": "https://example.com/user1.jpg"

        },
    }
    ...
]
```
#### Response Status Codes: 200

| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the blog |
| title | String | The title of the blog |
| content | String | The content of the blog |
| author | Object | The author of the blog |

Respose Author

| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the author |
| name | String | The name of the author |
| email | String | The email of the author |
| imageUrl | String | The URL of the author's profile image |

### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |



### GET /api/blogs/:id
Get a blog by id

```bash
curl -X GET http://localhost:8080/api/blogs/b1
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

Response 

```json
{
    "id": "b1",
    "title": "blog1",
    "content": "blog1 content",
    "author": {
        "id": "u1",
        "name": "user1",
        "email": "t1@t.com",
        "imageUrl": "https://example.com/user1.jpg"
    }
}
```

#### Response Status Codes: 200

Response Entity

| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the blog |
| title | String | The title of the blog |
| content | String | The content of the blog |
| author | Object | The author of the blog |

Respose Author

| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the author |
| name | String | The name of the author |
| email | String | The email of the author |    
| imageUrl | String | The URL of the author's profile image |


#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |

### POST /api/blogs
Create a new blog   

```bash
curl -X POST http://localhost:8080/api/blogs
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
    -d '{
        "title": "blog1",
        "content": "blog1 content"
    }'
```

#### Response Status Codes: 201
| Code | Description | Response Body |
| --- | --- | --- |
| 201 | Created | The blog was successfully created |
| id | String | Unique identifier for the blog |
| title | String | The title of the blog |
| content | String | The content of the blog |
| author | Object | The author of the blog |

Respose Author
| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the author |
| name | String | The name of the author |
| email | String | The email of the author |  
| imageUrl | String | The URL of the author's profile image |

Query Params
| Field | Type | Description |
| --- | --- | --- |
| page | Integer | The page number |
| size | Integer | The number of items per page |


#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |


### PUT /api/blogs/:id
Update a blog by id

```bash
curl -X PUT http://localhost:8080/api/blogs/b1
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
    -d '{
        "title": "blog1 updated",
        "content": "blog1 content updated"
    }'
```

#### Response Status Codes: 200
| Code | Description | Response Body |
| --- | --- | --- |
| 200 | OK | The blog was successfully updated |
| id | String | Unique identifier for the blog |
| title | String | The title of the blog |
| content | String | The content of the blog |
| author | Object | The author of the blog |


Respose Author
| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the author |
| name | String | The name of the author |
| email | String | The email of the author |  
| imageUrl | String | The URL of the author's profile image |


#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |


### DELETE /api/blogs/:id
Delete a blog by id

```bash
curl -X DELETE http://localhost:8080/api/blogs/b1
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

#### Response Status Codes: 200
| Code | Description | Response Body |
| --- | --- | --- |
| 200 | OK | The blog was successfully deleted |
| message | String | A success message |

#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |



# Authors
### GET /api/authors
Get all authors

```bash
curl -X GET http://localhost:8080/api/authors
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
``` 

#### Response Status Codes: 200
| Code | Description | Response Body |
| --- | --- | --- |
| 200 | OK | The authors were successfully retrieved |
| authors | Array | An array of authors |

Respose Author
| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the author |
| name | String | The name of the author |
| email | String | The email of the author |
| imageUrl | String | The URL of the author's profile image |

Query Params
| Field | Type | Description |
| --- | --- | --- |
| page | Integer | The page number |
| size | Integer | The number of items per page |

#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |

### GET /api/authors/:id
Get an author by id 

```bash
curl -X GET http://localhost:8080/api/authors/u1
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

#### Response Status Codes: 200
| Code | Description | Response Body |
| --- | --- | --- |
| 200 | OK | The author was successfully retrieved |
| author | Object | The author |

Respose Author
| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the author |
| name | String | The name of the author |
| email | String | The email of the author |
| imageUrl | String | The URL of the author's profile image |

#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |  


### GET /api/authors/:id/blogs
Get all blogs by author id

```bash
curl -X GET http://localhost:8080/api/authors/u1/blogs
    -H "Content-Type: application/json"
    -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

#### Response Status Codes: 200
| Code | Description | Response Body |
| --- | --- | --- |
| 200 | OK | The blogs were successfully retrieved |
| blogs | Array | An array of blogs |

Respose Blog
| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the blog |
| title | String | The title of the blog |
| content | String | The content of the blog |
| author | Object | The author of the blog |


Respose Author
| Field | Type | Description |
| --- | --- | --- |
| id | String | Unique identifier for the author |
| name | String | The name of the author |
| email | String | The email of the author |
| imageUrl | String | The URL of the author's profile image |


Query Params
| Field | Type | Description |
| --- | --- | --- |
| page | Integer | The page number |
| size | Integer | The number of items per page |

#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |


# Images

### Get /api/images/:filename
Get an image by filename

```bash
curl -X GET http://localhost:8080/api/images/u1.png
    -H "Content-Type: image/png"
```

#### Response Status Codes: 200
| Code | Description | Response Body |
| --- | --- | --- |
| 200 | OK | The image was successfully retrieved |
| image | Object | The image |

#### Response Status Codes: 400
| Code | Description | Response Body |
| --- | --- | --- |
| 400 | Bad request | Invalid request body |
| message | String | The error message |




