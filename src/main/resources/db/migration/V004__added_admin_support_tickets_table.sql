CREATE TABLE admin_support_tickets(
  id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
  is_active BOOLEAN NOT NULL,
  raised_by_email CHARACTER VARYING(255) NOT NULL,
  raised_by_name CHARACTER VARYING(255) NOT NULL,
  raised_by_account_type CHARACTER VARYING NOT NULL,
  description CHARACTER VARYING(1000) NOT NULL,
  ticket_issue CHARACTER VARYING (200) NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE trigger admin_support_tickets_creation_timestamp_trigger
   BEFORE INSERT ON admin_support_tickets
   for each row EXECUTE procedure creation_timestamp_handler();
   
CREATE trigger admin_support_tickets_updation_timestamp_trigger
   BEFORE UPDATE ON admin_support_tickets
   for each row EXECUTE procedure updation_timestamp_handler();
