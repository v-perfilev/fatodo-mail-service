package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Mail;
import com.persoff68.fatodo.repository.MailRepository;
import com.persoff68.fatodo.service.exception.ModelNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailStoreService {

    private final MailRepository mailRepository;

    public List<Mail> getListOfNotSent() {
        return mailRepository.findAllByStatus(Mail.Status.NEW, PageRequest.of(0, 10));
    }

    public void add(Mail mail) {
        mailRepository.save(mail);
    }

    public void changeStatus(Mail mail, Mail.Status status) {
        if (mail.getId() == null) {
            throw new ModelNotFoundException();
        }
        mail.setStatus(status);
        mailRepository.save(mail);
    }

    public void deleteSentMails() {
        mailRepository.deleteAllByStatus(Mail.Status.SENT);
    }

}
