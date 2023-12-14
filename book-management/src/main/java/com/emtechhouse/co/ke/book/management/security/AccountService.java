package com.emtechhouse.co.ke.book.management.security;

import com.emtechhouse.co.ke.book.management.customer.Customer;

public interface AccountService {
    UserAccount createUserAccount(Customer customer) ;
}
