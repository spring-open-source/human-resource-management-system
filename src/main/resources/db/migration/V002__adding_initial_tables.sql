CREATE TABLE master_countries(
  id SERIAL PRIMARY KEY, 
  code CHARACTER VARYING(5) NOT NULL,
  name CHARACTER VARYING(100) NOT NULL UNIQUE
);
   
CREATE TABLE companies (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  company_code CHARACTER VARYING(50) NOT NULL UNIQUE, 
  logo_url CHARACTER VARYING(255), 
  name CHARACTER VARYING(255) NOT NULL UNIQUE, 
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE trigger companies_creation_timestamp_trigger
   BEFORE INSERT ON companies
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger companies_updation_timestamp_trigger
   BEFORE UPDATE ON companies
   for each row EXECUTE procedure updation_timestamp_handler();

CREATE TABLE company_documents(
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  company_id UUID NOT NULL,
  name CHARACTER VARYING(255) NOT NULL,
  document_url CHARACTER VARYING (255) NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT company_docs_fkey_companies FOREIGN KEY (company_id)
        REFERENCES companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE trigger company_documents_creation_timestamp_trigger
   BEFORE INSERT ON company_documents
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger company_documents_updation_timestamp_trigger
   BEFORE UPDATE ON company_documents
   for each row EXECUTE procedure updation_timestamp_handler();
   
CREATE TABLE master_roles (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name CHARACTER VARYING(255) NOT NULL UNIQUE, 
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE trigger master_roles_creation_timestamp_trigger
   BEFORE INSERT ON master_roles
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger master_roles_updation_timestamp_trigger
   BEFORE UPDATE ON master_roles
   for each row EXECUTE procedure updation_timestamp_handler();
   
CREATE TABLE employee_daily_attendances (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  date DATE NOT NULL, 
  is_marked BOOLEAN NOT NULL, 
  is_present BOOLEAN
);
   
CREATE TABLE employees (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  first_name CHARACTER VARYING(255) NOT NULL,
  middle_name CHARACTER VARYING(255),
  last_name CHARACTER VARYING (255),
  email_id CHARACTER VARYING (255) NOT NULL UNIQUE,
  description CHARACTER VARYING (1000),
  status CHARACTER VARYING (1000),
  image_url CHARACTER VARYING(255) UNIQUE,
  gender CHARACTER VARYING(255),
  date_of_birth DATE,
  company_id UUID,
  country_id INTEGER,
  company_status INTEGER,
  employee_daily_attendance_id UUID,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT employees_fkey_compaies FOREIGN KEY (company_id)
        REFERENCES companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
  CONSTRAINT employees_fkey_countries FOREIGN KEY (country_id)
        REFERENCES master_countries (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
  CONSTRAINT employees_fkey_daily_attendance FOREIGN KEY (employee_daily_attendance_id)
        REFERENCES employee_daily_attendances (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE trigger employees_creation_timestamp_trigger
   BEFORE INSERT ON employees
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger employees_updation_timestamp_trigger
   BEFORE UPDATE ON employees
   for each row EXECUTE procedure updation_timestamp_handler();
   
CREATE TABLE employee_roles(
  id SERIAL PRIMARY KEY,
  employee_id UUID NOT NULL,
  role_id UUID NOT NULL,
  CONSTRAINT employee_roles_fkey_employees FOREIGN KEY (employee_id)
        REFERENCES employees (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
  CONSTRAINT employee_roles_fkey_roles FOREIGN KEY (role_id)
        REFERENCES master_roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE monthly_salary_details (
  id SERIAL PRIMARY KEY, 
  employee_id UUID NOT NULL,
  salary DECIMAL NOT NULL,
  bonus DECIMAL, 
  penalty DECIMAL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT employees_fkey_monthly_salary_details FOREIGN KEY (employee_id)
        REFERENCES employees (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION 
); 

CREATE trigger monthly_salary_details_creation_timestamp_trigger
   BEFORE INSERT ON monthly_salary_details
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger monthly_salary_details_updation_timestamp_trigger
   BEFORE UPDATE ON monthly_salary_details
   for each row EXECUTE procedure updation_timestamp_handler();

CREATE TABLE employee_socials(
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  employee_id UUID NOT NULL,
  name CHARACTER VARYING (255) NOT NULL,
  url CHARACTER VARYING (255) NOT NULL UNIQUE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT employees_fkey_monthly_salary_details FOREIGN KEY (employee_id)
        REFERENCES employees (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION 
);

CREATE trigger socials_creation_timestamp_trigger
   BEFORE INSERT ON employee_socials
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger socials_updation_timestamp_trigger
   BEFORE UPDATE ON employee_socials
   for each row EXECUTE procedure updation_timestamp_handler();
   
CREATE TABLE company_events (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  company_id UUID NOT NULL,
  is_active BOOLEAN NOT NULL,
  heading CHARACTER VARYING(255) NOT NULL,
  description CHARACTER VARYING(1000) NOT NULL, 
  image_url CHARACTER VARYING(255),
  due_date DATE NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT employees_fkey_compaies FOREIGN KEY (company_id)
        REFERENCES companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE trigger company_events_creation_timestamp_trigger
   BEFORE INSERT ON company_events
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger company_events_updation_timestamp_trigger
   BEFORE UPDATE ON company_events
   for each row EXECUTE procedure updation_timestamp_handler();
   
CREATE TABLE employee_issues (
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  title CHARACTER VARYING(100) NOT NULL,
  description CHARACTER VARYING(1000) NOT NULL, 
  company_id UUID NOT NULL, 
  employee_id UUID NOT NULL,
  is_active boolean NOT NULL,
  issue_type CHARACTER VARYING(255), 
  response CHARACTER VARYING(1000),
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT employee_issues_fkey_compaies FOREIGN KEY (company_id)
      REFERENCES companies (id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION,
  CONSTRAINT employee_issues_fkey_employees FOREIGN KEY (employee_id)
      REFERENCES employees (id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
);   

CREATE trigger employee_issues_creation_timestamp_trigger
   BEFORE INSERT ON employee_issues
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger employee_issues_updation_timestamp_trigger
   BEFORE UPDATE ON employee_issues
   for each row EXECUTE procedure updation_timestamp_handler();


CREATE TABLE attendance_records(
  id SERIAL PRIMARY KEY,
  company_id UUID NOT NULL,
  employee_id UUID NOT NULL,
  was_marked BOOLEAN NOT NULL,
  attendance BOOLEAN NOT NULL,
  day INTEGER NOT NULL,
  month INTEGER NOT NULL,
  year INTEGER NOT NULL  
);

CREATE TABLE salary_records(
  id SERIAL PRIMARY KEY,
  company_id UUID NOT NULL,
  employee_id UUID NOT NULL,
  total_amount_payable DECIMAL NOT NULL,
  bonus_for_month DECIMAL NOT NULL, 
  penalty_for_month DECIMAL NOT NULL,
  paid boolean NOT NULL,
  month INTEGER NOT NULL,
  year INTEGER NOT NULL  
);
