# OrderManagementSystem
1)Need to configure git uri for configserverdemo app
in my case 
spring.cloud.config.server.git.uri= file:///E:/git_config/configserverrepo

2)Need to maintain Order while making service up
i)configserverdemo
ii)eureka-service
iii)ProductManagementService
iv)OrderManagementSystem

3)Order of testing APIs 
i)http://127.0.0.1:7008/productService/loadProducts
{
    "timestamp": "2020-08-05T17:31:05.750+0000",
    "status": "SUCCESS",
    "data": [
        {
            "id": 1,
            "code": 1,
            "name": "Product1",
            "quantity": 1000,
            "cost": 11
        },
        {
            "id": 2,
            "code": 2,
            "name": "Product2",
            "quantity": 1000,
            "cost": 12
        },
        {
            "id": 3,
            "code": 3,
            "name": "Product3",
            "quantity": 1000,
            "cost": 13
        },
        {
            "id": 4,
            "code": 4,
            "name": "Product4",
            "quantity": 1000,
            "cost": 14
        },
        {
            "id": 5,
            "code": 5,
            "name": "Product5",
            "quantity": 1000,
            "cost": 15
        },
        {
            "id": 6,
            "code": 6,
            "name": "Product6",
            "quantity": 1000,
            "cost": 16
        },
        {
            "id": 7,
            "code": 7,
            "name": "Product7",
            "quantity": 1000,
            "cost": 17
        },
        {
            "id": 8,
            "code": 8,
            "name": "Product8",
            "quantity": 1000,
            "cost": 18
        },
        {
            "id": 9,
            "code": 9,
            "name": "Product9",
            "quantity": 1000,
            "cost": 19
        },
        {
            "id": 10,
            "code": 10,
            "name": "Product10",
            "quantity": 1000,
            "cost": 20
        },
        {
            "id": 11,
            "code": 11,
            "name": "Product11",
            "quantity": 1000,
            "cost": 21
        },
        {
            "id": 12,
            "code": 12,
            "name": "Product12",
            "quantity": 1000,
            "cost": 22
        },
        {
            "id": 13,
            "code": 13,
            "name": "Product13",
            "quantity": 1000,
            "cost": 23
        },
        {
            "id": 14,
            "code": 14,
            "name": "Product14",
            "quantity": 1000,
            "cost": 24
        },
        {
            "id": 15,
            "code": 15,
            "name": "Product15",
            "quantity": 1000,
            "cost": 25
        },
        {
            "id": 16,
            "code": 16,
            "name": "Product16",
            "quantity": 1000,
            "cost": 26
        },
        {
            "id": 17,
            "code": 17,
            "name": "Product17",
            "quantity": 1000,
            "cost": 27
        },
        {
            "id": 18,
            "code": 18,
            "name": "Product18",
            "quantity": 1000,
            "cost": 28
        },
        {
            "id": 19,
            "code": 19,
            "name": "Product19",
            "quantity": 1000,
            "cost": 29
        },
        {
            "id": 20,
            "code": 20,
            "name": "Product20",
            "quantity": 1000,
            "cost": 30
        },
        {
            "id": 21,
            "code": 21,
            "name": "Product21",
            "quantity": 1000,
            "cost": 31
        },
        {
            "id": 22,
            "code": 22,
            "name": "Product22",
            "quantity": 1000,
            "cost": 32
        },
        {
            "id": 23,
            "code": 23,
            "name": "Product23",
            "quantity": 1000,
            "cost": 33
        },
        {
            "id": 24,
            "code": 24,
            "name": "Product24",
            "quantity": 1000,
            "cost": 34
        },
        {
            "id": 25,
            "code": 25,
            "name": "Product25",
            "quantity": 1000,
            "cost": 35
        },
        {
            "id": 26,
            "code": 26,
            "name": "Product26",
            "quantity": 1000,
            "cost": 36
        },
        {
            "id": 27,
            "code": 27,
            "name": "Product27",
            "quantity": 1000,
            "cost": 37
        },
        {
            "id": 28,
            "code": 28,
            "name": "Product28",
            "quantity": 1000,
            "cost": 38
        },
        {
            "id": 29,
            "code": 29,
            "name": "Product29",
            "quantity": 1000,
            "cost": 39
        },
        {
            "id": 30,
            "code": 30,
            "name": "Product30",
            "quantity": 1000,
            "cost": 40
        },
        {
            "id": 31,
            "code": 31,
            "name": "Product31",
            "quantity": 1000,
            "cost": 41
        },
        {
            "id": 32,
            "code": 32,
            "name": "Product32",
            "quantity": 1000,
            "cost": 42
        },
        {
            "id": 33,
            "code": 33,
            "name": "Product33",
            "quantity": 1000,
            "cost": 43
        },
        {
            "id": 34,
            "code": 34,
            "name": "Product34",
            "quantity": 1000,
            "cost": 44
        },
        {
            "id": 35,
            "code": 35,
            "name": "Product35",
            "quantity": 1000,
            "cost": 45
        },
        {
            "id": 36,
            "code": 36,
            "name": "Product36",
            "quantity": 1000,
            "cost": 46
        },
        {
            "id": 37,
            "code": 37,
            "name": "Product37",
            "quantity": 1000,
            "cost": 47
        },
        {
            "id": 38,
            "code": 38,
            "name": "Product38",
            "quantity": 1000,
            "cost": 48
        },
        {
            "id": 39,
            "code": 39,
            "name": "Product39",
            "quantity": 1000,
            "cost": 49
        },
        {
            "id": 40,
            "code": 40,
            "name": "Product40",
            "quantity": 1000,
            "cost": 50
        },
        {
            "id": 41,
            "code": 41,
            "name": "Product41",
            "quantity": 1000,
            "cost": 51
        },
        {
            "id": 42,
            "code": 42,
            "name": "Product42",
            "quantity": 1000,
            "cost": 52
        },
        {
            "id": 43,
            "code": 43,
            "name": "Product43",
            "quantity": 1000,
            "cost": 53
        },
        {
            "id": 44,
            "code": 44,
            "name": "Product44",
            "quantity": 1000,
            "cost": 54
        },
        {
            "id": 45,
            "code": 45,
            "name": "Product45",
            "quantity": 1000,
            "cost": 55
        },
        {
            "id": 46,
            "code": 46,
            "name": "Product46",
            "quantity": 1000,
            "cost": 56
        },
        {
            "id": 47,
            "code": 47,
            "name": "Product47",
            "quantity": 1000,
            "cost": 57
        },
        {
            "id": 48,
            "code": 48,
            "name": "Product48",
            "quantity": 1000,
            "cost": 58
        },
        {
            "id": 49,
            "code": 49,
            "name": "Product49",
            "quantity": 1000,
            "cost": 59
        },
        {
            "id": 50,
            "code": 50,
            "name": "Product50",
            "quantity": 1000,
            "cost": 60
        },
        {
            "id": 51,
            "code": 51,
            "name": "Product51",
            "quantity": 1000,
            "cost": 61
        },
        {
            "id": 52,
            "code": 52,
            "name": "Product52",
            "quantity": 1000,
            "cost": 62
        },
        {
            "id": 53,
            "code": 53,
            "name": "Product53",
            "quantity": 1000,
            "cost": 63
        },
        {
            "id": 54,
            "code": 54,
            "name": "Product54",
            "quantity": 1000,
            "cost": 64
        },
        {
            "id": 55,
            "code": 55,
            "name": "Product55",
            "quantity": 1000,
            "cost": 65
        },
        {
            "id": 56,
            "code": 56,
            "name": "Product56",
            "quantity": 1000,
            "cost": 66
        },
        {
            "id": 57,
            "code": 57,
            "name": "Product57",
            "quantity": 1000,
            "cost": 67
        },
        {
            "id": 58,
            "code": 58,
            "name": "Product58",
            "quantity": 1000,
            "cost": 68
        },
        {
            "id": 59,
            "code": 59,
            "name": "Product59",
            "quantity": 1000,
            "cost": 69
        },
        {
            "id": 60,
            "code": 60,
            "name": "Product60",
            "quantity": 1000,
            "cost": 70
        },
        {
            "id": 61,
            "code": 61,
            "name": "Product61",
            "quantity": 1000,
            "cost": 71
        },
        {
            "id": 62,
            "code": 62,
            "name": "Product62",
            "quantity": 1000,
            "cost": 72
        },
        {
            "id": 63,
            "code": 63,
            "name": "Product63",
            "quantity": 1000,
            "cost": 73
        },
        {
            "id": 64,
            "code": 64,
            "name": "Product64",
            "quantity": 1000,
            "cost": 74
        },
        {
            "id": 65,
            "code": 65,
            "name": "Product65",
            "quantity": 1000,
            "cost": 75
        },
        {
            "id": 66,
            "code": 66,
            "name": "Product66",
            "quantity": 1000,
            "cost": 76
        },
        {
            "id": 67,
            "code": 67,
            "name": "Product67",
            "quantity": 1000,
            "cost": 77
        },
        {
            "id": 68,
            "code": 68,
            "name": "Product68",
            "quantity": 1000,
            "cost": 78
        },
        {
            "id": 69,
            "code": 69,
            "name": "Product69",
            "quantity": 1000,
            "cost": 79
        },
        {
            "id": 70,
            "code": 70,
            "name": "Product70",
            "quantity": 1000,
            "cost": 80
        },
        {
            "id": 71,
            "code": 71,
            "name": "Product71",
            "quantity": 1000,
            "cost": 81
        },
        {
            "id": 72,
            "code": 72,
            "name": "Product72",
            "quantity": 1000,
            "cost": 82
        },
        {
            "id": 73,
            "code": 73,
            "name": "Product73",
            "quantity": 1000,
            "cost": 83
        },
        {
            "id": 74,
            "code": 74,
            "name": "Product74",
            "quantity": 1000,
            "cost": 84
        },
        {
            "id": 75,
            "code": 75,
            "name": "Product75",
            "quantity": 1000,
            "cost": 85
        },
        {
            "id": 76,
            "code": 76,
            "name": "Product76",
            "quantity": 1000,
            "cost": 86
        },
        {
            "id": 77,
            "code": 77,
            "name": "Product77",
            "quantity": 1000,
            "cost": 87
        },
        {
            "id": 78,
            "code": 78,
            "name": "Product78",
            "quantity": 1000,
            "cost": 88
        },
        {
            "id": 79,
            "code": 79,
            "name": "Product79",
            "quantity": 1000,
            "cost": 89
        },
        {
            "id": 80,
            "code": 80,
            "name": "Product80",
            "quantity": 1000,
            "cost": 90
        },
        {
            "id": 81,
            "code": 81,
            "name": "Product81",
            "quantity": 1000,
            "cost": 91
        },
        {
            "id": 82,
            "code": 82,
            "name": "Product82",
            "quantity": 1000,
            "cost": 92
        },
        {
            "id": 83,
            "code": 83,
            "name": "Product83",
            "quantity": 1000,
            "cost": 93
        },
        {
            "id": 84,
            "code": 84,
            "name": "Product84",
            "quantity": 1000,
            "cost": 94
        },
        {
            "id": 85,
            "code": 85,
            "name": "Product85",
            "quantity": 1000,
            "cost": 95
        },
        {
            "id": 86,
            "code": 86,
            "name": "Product86",
            "quantity": 1000,
            "cost": 96
        },
        {
            "id": 87,
            "code": 87,
            "name": "Product87",
            "quantity": 1000,
            "cost": 97
        },
        {
            "id": 88,
            "code": 88,
            "name": "Product88",
            "quantity": 1000,
            "cost": 98
        },
        {
            "id": 89,
            "code": 89,
            "name": "Product89",
            "quantity": 1000,
            "cost": 99
        },
        {
            "id": 90,
            "code": 90,
            "name": "Product90",
            "quantity": 1000,
            "cost": 100
        },
        {
            "id": 91,
            "code": 91,
            "name": "Product91",
            "quantity": 1000,
            "cost": 101
        },
        {
            "id": 92,
            "code": 92,
            "name": "Product92",
            "quantity": 1000,
            "cost": 102
        },
        {
            "id": 93,
            "code": 93,
            "name": "Product93",
            "quantity": 1000,
            "cost": 103
        },
        {
            "id": 94,
            "code": 94,
            "name": "Product94",
            "quantity": 1000,
            "cost": 104
        },
        {
            "id": 95,
            "code": 95,
            "name": "Product95",
            "quantity": 1000,
            "cost": 105
        },
        {
            "id": 96,
            "code": 96,
            "name": "Product96",
            "quantity": 1000,
            "cost": 106
        },
        {
            "id": 97,
            "code": 97,
            "name": "Product97",
            "quantity": 1000,
            "cost": 107
        },
        {
            "id": 98,
            "code": 98,
            "name": "Product98",
            "quantity": 1000,
            "cost": 108
        },
        {
            "id": 99,
            "code": 99,
            "name": "Product99",
            "quantity": 1000,
            "cost": 109
        },
        {
            "id": 100,
            "code": 100,
            "name": "Product100",
            "quantity": 1000,
            "cost": 110
        }
    ]
}
ii)http://localhost:7007/orderService/testOrder
{
    "timestamp": "2020-08-05T17:31:17.890+0000",
    "status": "SUCCESS",
    "data": {
        "id": 3,
        "customerId": 1,
        "customerName": "Tester",
        "orderDate": "2020-08-05T17:31:17.890+0000",
        "shippingAddress": {
            "id": 1,
            "streetName": "100 feet",
            "village": "Madhapur",
            "district": null,
            "state": "Telangana",
            "pincode": "12345"
        },
        "items": [
            {
                "id": 1,
                "code": 1,
                "name": "Product1",
                "quantity": 1,
                "cost": 10
            },
            {
                "id": 2,
                "code": 2,
                "name": "Product2",
                "quantity": 1,
                "cost": 10
            },
            {
                "id": 3,
                "code": 3,
                "name": "Product3",
                "quantity": 1,
                "cost": 10
            },
            {
                "id": 4,
                "code": 4,
                "name": "Product4",
                "quantity": 1,
                "cost": 10
            },
            {
                "id": 5,
                "code": 5,
                "name": "Product5",
                "quantity": 1,
                "cost": 10
            },
            {
                "id": 6,
                "code": 6,
                "name": "Product6",
                "quantity": 1,
                "cost": 10
            },
            {
                "id": 7,
                "code": 7,
                "name": "Product7",
                "quantity": 1,
                "cost": 10
            },
            {
                "id": 8,
                "code": 8,
                "name": "Product8",
                "quantity": 1,
                "cost": 10
            },
            {
                "id": 9,
                "code": 9,
                "name": "Product9",
                "quantity": 1,
                "cost": 10
            },
            {
                "id": 10,
                "code": 10,
                "name": "Product10",
                "quantity": 1,
                "cost": 10
            }
        ],
        "totalAmount": 155.0
    }
}

4)Enabled swagger documentation
i)http://localhost:7007/swagger-ui.html
ii)http://localhost:7008/swagger-ui.html

