# CLASS NOTES: 
prompt injections 


# PRE READS ..  

# Access control framework¶ https://docs.snowflake.com/en/user-guide/security-access-control-overview

Discretionary Access Control (DAC): Each object has an owner, who can in turn grant access to that object.

Role-based Access Control (RBAC): Access privileges are assigned to roles, which are in turn assigned to users.

User-based Access Control (UBAC): Access privileges are assigned directly to users. Access control considers privileges assigned directly to users only when USE SECONDARY ROLE is set to ALL.

                        ┌──────────────┐
                        │ Organization │
                        └──────┬───────┘
                               │
                        ┌──────▼──────┐
                        │   Account   │
                        └──┬──┬──┬──┬─┘
              ┌────────────┘  │  │  └───────────────────┐
              │        ┌──────┘  └──────┐               │
              ▼        ▼                ▼        ▼       ▼
        ┌─────────┐ ┌──────────┐  ┌─────────┐ ┌──────┐ ┌─────────────────┐
        │Warehouse│ │ Database │  │  Role   │ │ User │ │Other account    │
        └─────────┘ └────┬─────┘  └─────────┘ └──────┘ │objects          │
                         │                             └─────────────────┘
                    ┌────▼─────┐
                    │  Schema  │◄────────────────────────────────────┐
                    └────┬─────┘                                     │
                         │                                    ┌──────┴───────┐
                         │                                    │Database Role │
                         │                                    └──────────────┘
        ┌────────┬───────┼─────────┬──────────────┬──────┐
        ▼        ▼        ▼        ▼              ▼      ▼
   ┌───────┐ ┌──────┐ ┌───────┐ ┌───────────┐ ┌─────┐ ┌──────────────┐
   │ Table │ │ View │ │ Stage │ │  Stored   │ │ UDF │ │Other schema  │
   └───────┘ └──────┘ └───────┘ │ Procedure │ └─────┘ │objects       │
                                └───────────┘         └──────────────┘



Role hierarchy and privilege inheritance
                              ┌───────────────┐
                              │ ACCOUNTADMIN  │
                              └───────┬───────┘
                                      ▲
                   ┌──────────────────┴──────────────────┐
                   │                                     │
          ┌────────┴──────┐                    ┌─────────┴──────┐
          │ SECURITYADMIN │                    │   SYSADMIN     │
          └────────┬──────┘                    └────┬──────┬────┘
                   ▲                                ▲      ▲
          ┌────────┴──────┐               ┌─────────┘  ┌───┴──────────┐
          │  USERADMIN    │               │            │              │
          └────────┬──────┘        ┌──────┴────┐  ┌────┴──────┐       │
                   ▲               │Custom Role│  │Custom Role│       │
                   │               └───────────┘  └───────────┘       │
                   │                                        ▲         │
                   │                               ┌────────┴───┐     │
                   │                               │Custom Role │     │
                   │                               └────────┬───┘     │
                   │                                        ▲         │
                   │                               ┌────────┴───┐     │
                   │                               │Custom Role │     │
                   │                               └────────┬───┘     │
                   │                                        ▲         │
          ┌────────┴──────┐                                 │         │
          │    PUBLIC     │          ╔════════════════════════════════╗ 
          └───────────────┘          ║  ┌─────────────┐             ║ │
                                     ║  │Database Role│◄────────────╝ │
                                     ║  └──────┬──────┘               │
                                     ║         ▲                      │
                                     ║  ┌──────┴──────┐               │
                                     ║  │Database Role│               │
                                     ║  └─────────────┘               │
                                     ╚════════════════════════════════╝


Privileges are managed using the following commands:

GRANT <privileges> … TO ROLE
REVOKE <privileges> … FROM ROLE
GRANT <privileges> … TO USER
REVOKE <privileges> … FROM USER


| Role | Description |
|------|-------------|
| **GLOBALORGADMIN** | Manages account lifecycle and org-level usage; exists only in the organization account. |
| **ORGADMIN**       | Manages org-level operations from a regular account; being phased out in favor of GLOBALORGADMIN. |
| **ACCOUNTADMIN**   | Top-level role that encapsulates SYSADMIN and SECURITYADMIN; grant to as few users as possible. |
| **SECURITYADMIN**  | Can manage all grants globally and create/manage users and roles via the MANAGE GRANTS privilege. |
| **USERADMIN**      | Dedicated solely to creating and managing users and roles via CREATE USER and CREATE ROLE privileges. |
| **SYSADMIN**       | Creates and manages warehouses, databases, and other account objects; recommended top of the custom role hierarchy. |
| **PUBLIC**         | Pseudo-role automatically granted to every user and role; anything it owns is accessible by everyone. |