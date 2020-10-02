package contracts.mailcontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'send reset password mail'
    description 'should return status 200'
    request {
        method POST()
        url("/api/mail/reset-password")
        headers {
            contentType applicationJson()
            header 'Authorization': $(
                    consumer(containing("Bearer")),
                    producer("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcm5hbWUiOiJ0ZXN0X3N5c3RlbSIsImF1dGhvcml0aWVzIjoiUk9MRV9TWVNURU0iLCJpYXQiOjAsImV4cCI6MzI1MDM2NzY0MDB9.EV6TMwQSB2XSTnQuB6LQbLETQmWEullfxSOmGDrlsdk93DDWfqr3VQGti6pMmmbUfgCyP9yyWjlWK50dYHYnEg")
            )
        }
        body(
                "language": $(
                        consumer(anyNonBlankString()),
                        producer("en")
                ),
                "email": $(
                        consumer(email()),
                        producer("test@email.com")
                ),
                "username": $(
                        consumer(regex(".{5,50}")),
                        producer("test_username")
                ),
                "code": anyNonBlankString()
        )
    }
    response {
        status 200
    }
}
