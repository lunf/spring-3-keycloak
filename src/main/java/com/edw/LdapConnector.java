package com.edw;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Hashtable;

public class LdapConnector {
    public static void main(String[] args) throws Exception {
        LdapContext context = getLdapContext();
        getUserBasicAttributes("joe.done", context);
    }

    public static LdapContext getLdapContext(){
        LdapContext ctx = null;
        try{
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
            env.put(Context.SECURITY_PRINCIPAL, "admin_username");
            env.put(Context.SECURITY_CREDENTIALS, "admin_password");
            env.put(Context.PROVIDER_URL, "ldap://localhost:389");
            ctx = new InitialLdapContext(env, null);
            System.out.println("Connection Successful.");
        }catch(NamingException nex){
            System.out.println("LDAP Connection: FAILED");
            nex.printStackTrace();
        }
        return ctx;
    }

    public static void getUserBasicAttributes(String username, LdapContext ctx) {
        try {

            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = { "distinguishedName",
                    "sn",
                    "givenname",
                    "mail",
                    "telephonenumber"};
            // constraints.setReturningAttributes(attrIDs);
            //First input parameter is search bas, it can be "CN=Users,DC=YourDomain,DC=com"
            //Second Attribute can be uid=username
            NamingEnumeration answer = ctx.search("DC=company,DC=com", "sAMAccountName="
                    + username, constraints);
            if (answer.hasMore()) {
                Attributes attrs = ((SearchResult) answer.next()).getAttributes();
                System.out.println("distinguishedName "+ attrs.get("distinguishedName"));
                System.out.println("sAMAccountName "+ attrs.get("sAMAccountName"));
                System.out.println("sAMAccountType "+ attrs.get("sAMAccountType"));
                System.out.println("givenname "+ attrs.get("givenname"));
                System.out.println("sn "+ attrs.get("sn"));
                System.out.println("mail "+ attrs.get("mail"));
                System.out.println("userPrincipalName "+ attrs.get("userPrincipalName"));
                System.out.println("telephonenumber "+ attrs.get("telephonenumber"));

                System.out.println("whenChanged "+ attrs.get("whenChanged"));

                String whenChanged = attrs.get("whenChanged").get().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SX");
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(whenChanged,formatter);

                System.out.println(offsetDateTime);


                System.out.println("accountExpires "+ attrs.get("accountExpires"));

                String accountExpires = attrs.get("accountExpires").get().toString();

                // convert a Win32 filetime
                long millis = (Long.parseLong(accountExpires) / 10000L) - + 11644473600000L;

                Timestamp stamp = new Timestamp(millis);
                Date date = new Date(stamp.getTime());
                System.out.println(date);


                System.out.println("pwdLastSet "+ attrs.get("pwdLastSet"));

                String lastPassChange = attrs.get("pwdLastSet").get().toString();

                // convert a Win32 filetime
                millis = (Long.parseLong(lastPassChange) / 10000L) - + 11644473600000L;

                stamp = new Timestamp(millis);
                date = new Date(stamp.getTime());
                System.out.println(date);


                for (NamingEnumeration it = attrs.getAll(); it.hasMore();) {
                    Attribute attribute = (Attribute) it.next();
                    System.out.println("attribute = " + attribute.getID());
                }
            }else{
                throw new Exception("Invalid User");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
