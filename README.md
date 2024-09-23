# Spring Boot - Restaurant Reservation System API

---

### API User Story


> #### Purpose
> We keep track the reservation of the restaurant

>#### Data
> Data we include in the reservation record includes name, phone number, email, reservation date and time, and number of guests.
> Each records has a status value (CREATED, UPDATED, NOTIFIED, CANCELLED). We keep copies of the records, even after have cancelled or notified.

> #### Actions
> The reservation records include getting the list of reservation details, creating, cancelling and updating. You will also recieve a notification via 
> SMS or email for your reserve details.

> #### Processing
> Each reservation has a unique identifier in the system. The system accepts every reservation with valid data.



---

###

### API Endpoints

Create Reservation: **POST** /api/reservations

Sample Request Body:
```
{
    "name":"francis",
    "phoneNumber":"09209725507",
    "email":"francis@gmail.com",
    "reservedDatetime": "2024-09-23T18:10:00",
    "guestCount": 5,
    "isSmsNotify": true,
    "isEmailNotify": false
}
```

Sample Response Body:
```
{
    "status": "CREATED",
    "data": null
}
```
###

Cancel Reservation: **DELETE** api/reservations/{id}


Sample Response Body:
```
{
    "status": "CANCELLED",
    "data": null
}
```

###

View Reservations: **GET** api/reservations


Sample Response Body:
```
{
    "status": null,
    "data": [
        {
            "id": "69f689c7-5621-4004-a171-7861d75beec4",
            "name": "francis 2",
            "phoneNumber": "09209725507",
            "email": "francis@gmail.com",
            "reservedDatetime": "2024-09-22T12:00:00",
            "guestCount": 4,
            "isSmsNotify": true,
            "isEmailNotify": true,
            "status": "CREATED",
            "createdAt": "2024-09-23T07:52:41.949611",
            "updatedAt": "2024-09-23T07:52:41.949626"
        },
        {
            "id": "c7a63d9a-ee52-4536-b2d2-2b097e28310f",
            "name": "francis",
            "phoneNumber": "09209725507",
            "email": "francis@gmail.com",
            "reservedDatetime": "2024-09-22T10:00:00",
            "guestCount": 5,
            "isSmsNotify": true,
            "isEmailNotify": true,
            "status": "CANCELLED",
            "createdAt": "2024-09-23T07:53:05.662783",
            "updatedAt": "2024-09-23T07:53:05.662806"
        }
    ]
}
```
###

Update Reservation: **PATCH** /api/reservations/{id}

Sample Request Body:
```
{
    "reservedDatetime": "2024-09-22T14:00:00",
    "guestCount": 4
}
```

Sample Response Body:
```
{
    "status": "UPDATED",
    "data": null
}
```
###

### Notes:

You may use the Postman Collections for manual testing,
located at _/src/main/resources/postman/_

###

-----


### Database:

Reservation Table

| Column   | Type      | Constraint  |
|----------|-----------|-------------|
| id  | UUID      | primary key |  
| name | VARCHAR   | 
| phoneNumber   | VARCHAR   |
| email | VARCHAR   |
| reservedDatetime   | TIMESTAMP |
| guestCount   | INTEGER   |
| isSmsNotify   | BOOLEAN   |
| isEmailNotify   | BOOLEAN |
| createdAt   | TIMESTAMP |
| updatedAt   | TIMESTAMP |

###