CREATE TABLE company_reports(
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  company_id UUID NOT NULL,
  report_type INTEGER NOT NULL,
  key CHARACTER VARYING(255) NOT NULL,
  month INTEGER NOT NULL,
  year INTEGER NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT employees_fkey_compaies FOREIGN KEY (company_id)
        REFERENCES companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE trigger company_reports_creation_timestamp_trigger
   BEFORE INSERT ON company_reports
   for each row EXECUTE procedure creation_timestamp_handler();
