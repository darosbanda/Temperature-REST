extern crate reqwest;
extern crate chrono;
#[macro_use]
extern crate serde_derive;
extern crate serde;

use std::fs::File;
use std::io::prelude::*;
use chrono::prelude::*;

#[derive(Serialize, Deserialize)]
struct TempReading {
    key: String,
    temperature: f32,
    date: DateTime<Local>
}

fn get_temp() -> f32 {
    let mut f = File::open("/sys/bus/w1/devices/28-0416a4f7ffff/w1_slave")
        .expect("File not found.");
    
    let mut contents = String::new();
    
    f.read_to_string(&mut contents)
        .expect("Could not read the file");
    
    let temp_string = &contents[contents.len()-6..contents.len()-1];
    temp_string.parse::<f32>().unwrap() / 1000.0
    
}

fn post_request(temp: f32, key: String) {
    let local: DateTime<Local> = Local::now();
    let tempreading = TempReading {
        date: local.to_owned(),
        key: key.to_owned(),
        temperature: temp.to_owned()
    };
    let client = reqwest::Client::new();
    let mut response = client.post("http://192.168.2.8:8080/sensors/temperatures/")
        .json(&tempreading)
        .send()
        .expect("Could not send TempReading");
    let mut buf = String::new();
    json.read_to_string(&mut buf).expect("Failed to read response");
    println!("{}", buf);
}

fn main() {
	let temp = get_temp();
	println!("{}", temp);
    let key = String::from("964f55fd39119c18f72192bb92a0971");
	post_request(temp, key);
}
