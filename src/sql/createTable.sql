use SIMULATOR;

DROP TABLE IF EXISTS servicepoint;
DROP TABLE IF EXISTS run;

CREATE TABLE IF NOT EXISTS run (
    run_id INT AUTO_INCREMENT,
    run_time BIGINT NOT NULL UNIQUE,
    simulator_time INT,
    PRIMARY KEY (run_id)
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS servicepoint (
    servicepoint_id INT AUTO_INCREMENT,
    run_id INT,
    name VARCHAR(255),
    busy_time DOUBLE,
    service_times INT,
    utilization DOUBLE,
    avg_servicetime DOUBLE,
    PRIMARY KEY (servicepoint_id),
    FOREIGN KEY (run_id)
           REFERENCES run(run_id)
           ON DELETE CASCADE
)  ENGINE=INNODB;

