CREATE TABLE DS_PILOT (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    RESOURCE_OWNER_ID INT,
    ORGANIZATION_ID INT,,
    FOREIGN KEY (ORGANIZATION_ID) REFERENCES DS_ORGANIZATION(ID)
);