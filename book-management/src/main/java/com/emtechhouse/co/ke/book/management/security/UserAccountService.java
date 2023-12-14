package com.emtechhouse.co.ke.book.management.security;


import com.emtechhouse.co.ke.book.management.auth.RoleService;
import com.emtechhouse.co.ke.book.management.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService implements UserDetailsService, AccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository
                .findUserAccountByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new UsernameNotFoundException("username not found")
                );
        return (UserDetails) new UserAccountDetails(userAccount);
    }
    @Override
    public UserAccount createUserAccount(Customer customer) {
        UserAccount userAccount = new UserAccount(customer.getEmail(),
                passwordEncoder.encode(customer.getPassword()))
                .addRoles(Collections.singleton(roleService.getRoleUSER()))
                .addCustomer(customer);
        Optional<UserAccount> userAccountByUsername = userAccountRepository
                .findUserAccountByUsername(userAccount.getUsername());
        if (userAccountByUsername.isEmpty()){
            return userAccountRepository.save(userAccount);
        }
        return userAccountByUsername.stream()
                .findFirst()
                .orElseThrow(() -> new
                        RuntimeException("User account not found"));
    }
}
