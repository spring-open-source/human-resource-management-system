CREATE TABLE rejected_employee_company_mapping(
  id SERIAL PRIMARY KEY,
  employee_id UUID NOT NULL,
  company_id UUID NOT NULL,
  is_active BOOLEAN NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL, 
  UNIQUE (employee_id, company_id),
  CONSTRAINT rejected_employee_fkey_employee FOREIGN KEY (employee_id)
        REFERENCES employees (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
  CONSTRAINT rejected_employee_fkey_company FOREIGN KEY (company_id)
        REFERENCES companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE trigger rejected_employee_creation_timestamp_trigger
   BEFORE INSERT ON rejected_employee_company_mapping
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger rejected_employee_updation_timestamp_trigger
   BEFORE UPDATE ON rejected_employee_company_mapping
   for each row EXECUTE procedure updation_timestamp_handler();
