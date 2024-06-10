
# iData API Documentation 🚀

Welcome to the official documentation for the iData API. This documentation provides detailed information on how to use and integrate with our platform to simplify API generation and management.

## 📑 Table of Contents

1. [Introduction](#introduction) 🌟
2. [User Management](#user-management) 👥
3. [Authentication](#authentication) 🔐
4. [Base URL](#base-url) 🌐
5. [Generate API](#generate-api)✨
    - [Schema Creation](#schema-creation) 
    - [Importing File](#import-file) 📁
    - [Data Scraping](#data-scraping)🔗
6. [Error Handling](#error-handling) ❌
7. [Rate Limits](#rate-limits) ⏳

---

## Introduction <a name="introduction"></a>

iData is an opensource mock API with enormous datasets integration for beginning developers. With iData, you can easily create, manage, and test APIs using intuitive tools and features.

---

## User Management <a name="user-management"></a>

Manage users with ease using the following endpoints:

- **Create User** ➕
  - **Description:** Endpoint to create a new user.
  - **HTTP Method:** POST
  - **Endpoint:** `/register`
  - **Request Body:**
    ```json
    {
      "email":"example@gmail.com" ,
      "firstName":"John" ,
      "lastName":"Jackson",
      "username":"noobdev",
      "password":"1234",
      "confirmedPassword":"1234"
    }
    ```
  - **Response Codes:**
    - `201 Created`: User created successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.

- **Get All Users** 📋
  - **Description:** Endpoint to retrieve a list of all users.
  - **HTTP Method:** GET
  - **Endpoint:** `/users`
  - **Response Codes:**
    - `200 OK`: List of users retrieved successfully.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.

- **Find User By UUID** 🔍
  - **Description:** Endpoint to search an existing user by uuid.
  - **HTTP Method:** GET
  - **Endpoint:** `/users/{uuid}`
  - **Response Example:**
      ```json
    {
      "id": "user_id",
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "username": "johndoe",
      "createdAt": "2024-06-02T12:00:00Z",
      "updatedAt": "2024-06-02T12:00:00Z"
    }
   ```
  - **Response Codes:**
    - `204 No Content`: User deleted successfully.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.

- **Delete User By ID** 🗑️
  - **Description:** Endpoint to delete an existing user.
  - **HTTP Method:** DELETE
  - **Endpoint:** `/users/{id}`
  - **Response Codes:**
    - `204 No Content`: User deleted successfully.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.

- **Disable User By UUID** 🔒
  - **Description:** Endpoint to disable a user account.
  - **HTTP Method:** PUT
  - **Endpoint:** `/users/{uuid}/disable-users`
  - **Response Codes:**
    - `200 OK`: User account disabled successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.

- **Enable User By UUID** 🔓
  - **Description:** Endpoint to enable a disabled user account.
  - **HTTP Method:** PUT
  - **Endpoint:** `/users/{uuid}/enable-users`
  - **Response Codes:**
    - `200 OK`: User account enabled successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.

- **Block User By UUID** 🚫
  - **Description:** Endpoint to block a user account.
  - **HTTP Method:** PUT
  - **Endpoint:** `/users/{uuid}/block-users`
  - **Response Codes:**
    - `200 OK`: User account blocked successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.

- **Update User By ID** 🔄
  - **Description:** Endpoint to update user information by ID.
  - **HTTP Method:** PATCH
  - **Endpoint:** `/users/{id}`
  - **Request Body:**
    ```json
    {
      "email":"example@gmail.com" ,
      "firstName":"John" ,
      "lastName":"Jackson",
      "username":"noobdev",
      "password":"1234",
      "confirmedPassword":"1234"
    }
    ```
  - **Response Codes:**
    - `200 OK`: User information updated successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.


- **Forgot Password** 🤔
  - **Description:** Endpoint to initiate the password reset process.
  - **HTTP Method:** POST
  - **Endpoint:** `/users/forget-password`
  - **Request Body:**
    ```json
    {
      "email": "user_email@example.com"
    }
    ```
  - **Response Codes:**
    - `200 OK`: Password reset initiated successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.

- **Reset Password** 🔑
  - **Description:** Endpoint to reset user password.
  - **HTTP Method:** POST
  - **Endpoint:** `/users/reset-password?token=e852415d-6bd7-41c7-ab9c-7daa18b982d8`
  - **Request Body:**
    ```json
    {
      "email": "user_email",
      "newPassword": "new_password123",
      "confirmedPassword": "new_password123"
    }
    ```
  - **Response Codes:**
    - `200 OK`: Password reset successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.

These endpoints provide comprehensive user management functionalities for your application.

---

## Authentication <a name="authentication"></a>

To access the iData API, you need to authenticate using JSON Web Tokens (JWT). You can obtain your JWT token by authenticating with your username and password. Upon successful authentication, the server will issue a JWT token, which you need to include in the Authorization header of your requests.

### 1. Obtain JWT Token

- **Login** 🔐
  - **Description:** Endpoint for user authentication and obtaining access and refresh tokens.
  - **HTTP Method:** POST
  - **Endpoint:** `/auth/login`
  - **Request Body:**
    ```json
    {
      "username": "your_username",
      "password": "your_password"
    }
    ```
  - **Response Example:**
    ```json
    {
      "type": "Bearer",
      "accessToken": "YOUR_ACCESS_TOKEN",
      "refreshToken": "YOUR_REFRESH_TOKEN"
    }
    ```
  - **Response Codes:**
    - `200 OK`: User authenticated and tokens generated successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.

---

- **Refresh Token** 🔄
  - **Description:** Endpoint to refresh the JWT token.
  - **HTTP Method:** POST
  - **Endpoint:** `/auth/refresh`
  - **Request Body:**
    ```json
    {
      "refreshToken": "your_refresh_token"
    }
    ```
  - **Response Example:**
    ```json
    {
      "type": "Bearer",
      "accessToken": "NEW_ACCESS_TOKEN",
      "refreshToken": "NEW_REFRESH_TOKEN"
    }
    ```
  - **Response Codes:**
    - `200 OK`: JWT token refreshed successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.

---
- **Update Password** 🔑
  - **Description:** Endpoint to update user password.
  - **HTTP Method:** PUT
  - **Endpoint:** `/auth/change-password`
  - **Request Body:**
    ```json
    {
      "oldPassword": "jj",
      "newPassword": "pp",
      "confirmPassword": "pp"
    }
    ```
  - **Response Codes:**
    - `200 OK`: Password updated successfully.
    - `400 Bad Request`: Invalid request parameters.
    - `401 Unauthorized`: Authentication failure.
    - `403 Forbidden`: Insufficient permissions for the operation.
### 2. Include JWT Token in Requests

Once you obtain the JWT token, include it in the Authorization header of your requests using the Bearer scheme.

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

By including the JWT token in the Authorization header, you can securely access protected endpoints within the iData API.

---


## Base URL <a name="base-url"></a>

The base URL for all API endpoints is:

```
https://api.idata.com/v1
```
📝 Note that this URL is provided as an example and is subject to change once the API is deployed to production. Developers should refer to the official documentation or announcements for the updated base URL once the API is live.

## Generate API <a name="generate-api"></a>

### 1. Schema Creation <a name="schema-creation"></a>

**Description:** Generate schemas to standardize API structures.

**HTTP Method:** POST

**Endpoint:** `/schemas`

**Request Body:**
```json
{
  "name": "Schema Name",
  "fields": [
    {
      "name": "Field 1",
      "type": "String"
    },
    {
      "name": "Field 2",
      "type": "Integer"
    }
  ]
}
```

**Response Example:**
```json
{
  "id": "schema_id",
  "name": "Schema Name",
  "fields": [
    {
      "name": "Field 1",
      "type": "String"
    },
    {
      "name": "Field 2",
      "type": "Integer"
    }
  ]
}
```

**Response Codes:**
- `201 Created`: Schema created successfully.
- `400 Bad Request`: Invalid request parameters.
- `401 Unauthorized`: Authentication failure.
- `403 Forbidden`: Insufficient permissions for the operation.

Apologies for the oversight. Here's the missing "Import File" feature:

### 2. Importing File <a name="import-file"></a>

**Description:** Import files to the iData platform.

**HTTP Method:** POST

**Endpoint:** `/import-file`

**Request Body:**
```json
{
  "file": "File content or path"
}
```

**Response Example:**
```json
{
  "message": "File imported successfully"
}
```

**Response Codes:**
- `200 OK`: File imported successfully.
- `400 Bad Request`: Invalid request parameters or file format.
- `401 Unauthorized`: Authentication failure.
- `403 Forbidden`: Insufficient permissions for the operation.

### 3. Data Scraping <a name="data-scraping"></a>

**Description:** Scrape data from various sources to create functional APIs.

**HTTP Method:** POST

**Endpoint:** `/scrape`

**Request Body:**
```json
{
  "url": "https://example.com/data",
  "format": "JSON"
}
```

**Response Example:**
```json
{
  "data": "Scraped data here"
}
```

**Response Codes:**
- `201 Created`: Schema created successfully.
- `400 Bad Request`: Invalid request parameters.
- `401 Unauthorized`: Authentication failure.
- `403 Forbidden`: Insufficient permissions for the operation.

## Error Handling <a name="error-handling"></a>

iData API uses standard HTTP status codes to indicate the success or failure of a request. Additionally, error responses include a JSON object with more details about the error.

Example error response:
```json
{
  "error": "Error message"
}
```

## Rate Limits <a name="rate-limits"></a>

To ensure fair usage of our API, we enforce the following rate limits on API requests:

- **Create API** 🚀
  - **Description:** Each user can create up to 3 APIs per day.
  - **Limit:** 3 APIs per day per user.

- **Import File** 📁
  - **Description:** Each user can import files with a total size of up to 1GB.
  - **Limit:** 1GB total file size per user.

These rate limits are in place to ensure equitable access to our API resources and maintain optimal performance for all users.
---

Feel free to customize this template with specific details about iData's endpoints, authentication methods, error handling, and support information to create comprehensive API documentation for your users.# test_deploy
