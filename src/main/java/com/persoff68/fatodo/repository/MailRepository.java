package com.persoff68.fatodo.repository;

import com.persoff68.fatodo.model.Mail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends MongoRepository<Mail, String> {

    List<Mail> findAllByStatus(Mail.Status status, Pageable pageable);

    void deleteAllByStatus(Mail.Status status);

}
