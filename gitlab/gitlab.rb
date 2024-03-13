gitlab_rails['omniauth_enabled'] = true
gitlab_rails['omniauth_allow_single_sign_on'] = ['oauth2_generic', 'openid_connect']
gitlab_rails['omniauth_sync_email_from_provider'] = ['oauth2_generic', 'openid_connect']
gitlab_rails['omniauth_sync_profile_from_provider'] = ['oauth2_generic', 'openid_connect']
gitlab_rails['omniauth_sync_profile_attributes'] = ['email']
gitlab_rails['omniauth_auto_sign_in_with_provider'] = 'openid_connect'
gitlab_rails['omniauth_block_auto_created_users'] = true
gitlab_rails['omniauth_auto_link_ldap_user'] = true
# gitlab_rails['omniauth_auto_link_saml_user'] = false
gitlab_rails['omniauth_auto_link_user'] = ['oauth2_generic', 'openid_connect']
gitlab_rails['omniauth_external_providers'] = ['oauth2_generic', 'openid_connect']
# gitlab_rails['omniauth_allow_bypass_two_factor'] = ['google_oauth2']
gitlab_rails['omniauth_providers'] = [
  {
    name: "openid_connect", # do not change this parameter
    label: "Keycloak", # optional label for login button, defaults to "Openid Connect"
    args: {
      name: "openid_connect",
      scope: ["openid", "profile", "email"],
      response_type: "code",
      issuer:  "http://dockerhost/realms/external",
      client_auth_method: "query",
      discovery: true,
      uid_field: "preferred_username",
      pkce: true,
      client_options: {
        identifier: "gitlab-client",
        secret: "BniyV90S7LYevocWFausJgOaA4kl2pWW",
        redirect_uri: "http://dockerhost:8084/users/auth/openid_connect/callback"
      }
    }
  }
]