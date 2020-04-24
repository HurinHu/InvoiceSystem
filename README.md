# InvoiceSystem
This is an invoice system for freelancer to manage their billing.

This project is based on Docker+PHP+MySql+Next.js+Bootstrap

## Environment

Docker: 18.09.7

### Backend

PHP: 7.2.9 <br />
MySql: 8.0.19 <br />
phpmyadmin: 5.0.1 <br />
CodeIgniter: 4.0.2

### Frontend

Next.js: 9.3.5 <br />
React: 16.12.0 <br />
typescript: 3.7.3 <br />

## Setup
### Backend
Copy `env` file to `.env`, change the parameter as you need.

Setup your vhost to point to public folder

### Frontend
  
  - Go to front end folder, use `npm install` to install the dependencies. 
  - Run `next build` to build the project. 
  - Run `next export` to export the static files, it will generate to `out` folder.

Put the output files to the server where you want it to serve.

