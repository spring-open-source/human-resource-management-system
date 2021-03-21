CREATE TABLE managers (
  id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
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
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL, 
  CONSTRAINT employees_fkey_compaies FOREIGN KEY (company_id)
        REFERENCES companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
  CONSTRAINT employees_fkey_countries FOREIGN KEY (country_id)
        REFERENCES master_countries (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE trigger managers_creation_timestamp_trigger
   BEFORE INSERT ON managers
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger managers_updation_timestamp_trigger
   BEFORE UPDATE ON managers
   for each row EXECUTE procedure updation_timestamp_handler();
   
CREATE TABLE manager_socials(
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  manager_id UUID NOT NULL,
  name CHARACTER VARYING (255) NOT NULL,
  url CHARACTER VARYING (255) NOT NULL UNIQUE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT employees_fkey_monthly_salary_details FOREIGN KEY (manager_id)
        REFERENCES managers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION 
);

CREATE trigger manager_socials_creation_timestamp_trigger
   BEFORE INSERT ON manager_socials
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger manager_socials_updation_timestamp_trigger
   BEFORE UPDATE ON manager_socials
   for each row EXECUTE procedure updation_timestamp_handler();
