### Nginx Load Balancer

upstream webapi {
	server 10.0.0.10;
	server 10.0.0.11;
	server 10.0.0.12; down;
}

server {
	listen 80;
	server_name localhost dockerhost;
	server_tokens off;

	location ^~ /.well-known/acme-challenge/ {
		default_type "text/plain";
		alias /var/www/certbot/.well-known/acme-challenge/;
	}

	location = /.well-known/acme-challenge/ {
		return 404;
	}

	location / {
		return 301 https://localhost$request_uri;
	}
}


server {
	listen 443 ssl http2;
	server_name localhost dockerhost;
	server_tokens off;

	ssl_certificate /etc/letsencrypt/live/thomasvilhena.com/fullchain.pem;
	ssl_certificate_key /etc/letsencrypt/live/thomasvilhena.com/privkey.pem;
	include /etc/letsencrypt/options-ssl-nginx.conf;
	ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;

	location / {
		proxy_pass http://webapi;
		proxy_set_header Host $http_host;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $scheme;
		proxy_set_header X-NginX-Proxy true;
		proxy_redirect off;
	}
}