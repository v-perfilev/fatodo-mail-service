package contracts.mailcontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'send notification mail'
    description 'should return status 200'
    request {
        method POST()
        url("/api/mail/notification")
        headers {
            contentType applicationJson()
            header 'Authorization': $(
                    consumer(containing("Bearer")),
                    producer("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMDAwMDAwMC0wMDAwLTAwMDAtMDAwMC0wMDAwMDAwMDAwMDAiLCJ1c2VybmFtZSI6InRlc3Rfc3lzdGVtIiwiYXV0aG9yaXRpZXMiOiJST0xFX1NZU1RFTSIsImlhdCI6MCwiZXhwIjozMjUwMzY3NjQwMH0.roNFKrM7NjEzXvRFRHlJXw0YxSFZ-4Afqvn7eFatpGF14olhXBvCvR9CkPkmlnlCAOYbpDO18krfi6SEX0tQ6Q")
            )
        }
        body(
                "language": $(
                        consumer(anyNonBlankString()),
                        producer("EN")
                ),
                "email": $(
                        consumer(email()),
                        producer("test@email.com")
                ),
                "username": $(
                        consumer(regex(".{5,50}")),
                        producer("test_username")
                ),
                "message": $(
                        consumer(anyNonBlankString()),
                        producer("test_message")
                ),
                "url": $(
                        consumer(anyNonBlankString()),
                        producer("test_url")
                )
        )
    }
    response {
        status 200
    }
}
