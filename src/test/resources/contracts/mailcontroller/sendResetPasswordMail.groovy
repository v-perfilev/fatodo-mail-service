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
                    producer("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4ZjlhN2NhZS03M2M4LTRhZDYtYjEzNS01YmQxMDliNTFkMmUiLCJ1c2VybmFtZSI6InRlc3RfdXNlciIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjowLCJleHAiOjMyNTAzNjc2NDAwfQ.Go0MIqfjREMHOLeqoX2Ej3DbeSG7ZxlL4UAvcxqNeO-RgrKUCrgEu77Ty1vgR_upxVGDAWZS-JfuSYPHSRtv-w")
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
