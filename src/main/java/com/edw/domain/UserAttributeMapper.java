package com.edw.domain;

import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.time.Instant;

public class UserAttributeMapper implements AttributesMapper<UserDto> {
    @Override
    public UserDto mapFromAttributes(Attributes attributes) throws NamingException {

        String displayName = attributes.get("displayName").get().toString();
        String accountName = attributes.get("sAMAccountName").get().toString();
        String email = attributes.get("mail").get().toString();
        String lastPassChange = attributes.get("pwdLastSet").get().toString();

        // convert a Win32 filetime
        long millis = (Long.parseLong(lastPassChange) / 10000L) - + 11644473600000L;

        Instant instant = Instant.ofEpochMilli(millis);

        return UserDto.builder()
                .displayName(displayName)
                .accountName(accountName)
                .email(email)
                .pwdLastSet(instant)
                .build();
    }
}
