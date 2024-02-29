CREATE TABLE Datos_Armas (
    ID_arma SERIAL PRIMARY KEY,
    Nombre_arma VARCHAR(100),
    Damage_LMB INTEGER,
    Damage_RMB INTEGER,
    Kill_Award VARCHAR(50),
    Running_Speed FLOAT,
    Side VARCHAR(50)
);

CREATE TABLE Datos_Llaves (
    Nombre_llave VARCHAR(100),
    Precio_llave FLOAT(10),
    Caja_que_abre VARCHAR(255)
);

CREATE TABLE Nombre_Cajas (
    ID_caja SERIAL PRIMARY KEY,
    Nombre_caja VARCHAR(100)
);

CREATE TABLE Datos_Skins (
    ID_skin SERIAL PRIMARY KEY,
    Nombre_caja VARCHAR(100),
    Nombre_skin VARCHAR(100)
);
