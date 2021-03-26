CREATE TABLE company_join_invitations(
  id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name CHARACTER VARYING(255) NOT NULL,
  email_id CHARACTER VARYING(255) NOT NULL,
  company_id UUID NOT NULL,
  sent_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  UNIQUE(email_id, company_id),
  CONSTRAINT company_join_invitations_fkey_companies FOREIGN KEY (company_id)
        REFERENCES companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
