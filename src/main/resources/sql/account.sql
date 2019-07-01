CREATE TABLE account
(
    Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Username VARCHAR(50) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    CreatedAt DATETIME DEFAULT(getdate()),
    UpdatedAt DATETIME DEFAULT(getdate())
);
