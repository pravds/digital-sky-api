CREATE TABLE DS_ORGANIZATION_ADDRESS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ORGANIZATION_ID INT,
    ADDRESS_ID INT,
    TYPE VARCHAR(10),
    FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION(ID)
    FOREIGN KEY (ADDRESS_ID) REFERENCES DS_ADDRESS(ID)
);