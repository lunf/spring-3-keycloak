package com.edw.business;

import com.edw.domain.UserAttributeMapper;
import com.edw.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    public List<UserDto> findByName(String name) {
        LdapQuery q = query().where("objectclass").is("person").and("cn").whitespaceWildcardsLike(name);
        return ldapTemplate.search(q, new UserAttributeMapper());
    }

    public UserDto findOne(String accountName) {
        Name dn = LdapNameBuilder.newInstance().add("OU", "COMPANY").build();
        LdapQuery q = query().base(dn).where("sAMAccountName").is(accountName);
        return ldapTemplate.search(q, new UserAttributeMapper()).stream().findFirst().orElse(null);
    }

    public void updatePassword(UserDto p, String password) {
        Attribute attr = new BasicAttribute("userPassword", password);
        ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
        ldapTemplate.modifyAttributes(buildDn(p), new ModificationItem[] { item });
    }

    public void updateLastName(UserDto p, String lastName) {
        Attribute attr = new BasicAttribute("sn", lastName);
        ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
        ldapTemplate.modifyAttributes(buildDn(p), new ModificationItem[] { item });
    }

    private Name buildDn(UserDto p) {
        return LdapNameBuilder.newInstance().add("OU", "COMPANY").add("sAMAccountName", p.getAccountName()).build();
    }
}
