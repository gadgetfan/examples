package com.selectyour.model.client;

import com.selectyour.model.namedentity.NamedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * service implementation for ClientService
 */
@Service
public final class ClientServiceImpl extends NamedServiceImpl<Client, ClientRepository> implements ClientService {
    public static final Long DEFAULT_CLIENT_ID = 1L;

    @Autowired
    @Override
    protected void setRepo(ClientRepository repo) {
        super.setRepo(repo);
    }
}
