import React from "react";
import { Text, View, AsyncStorage, StyleSheet, TextInput, Button, Alert } from 'react-native';
const URL = require("../package.json").serverURL;

class Login extends React.Component{
	static navigationOptions = {title:"Log in"};

	constructor(){
		super();
		this.state = {data:"", err:"", username:"", password:"", loggedIn:false};
	}

	async _tokenSave (item, value) {
		try{
			await AsyncStorage.setItem(item, value);
		} catch (error) {
			console.log("Async storage: " + error.message);
		}
	}
	
	saveData = (key, value) => {
		AsyncStorage.setItem(key, value);
		this.setState({key:value});
	}

	getData = (key) => {
		AsyncStorage.getItem(key).then((value) =>{
			this.setState({key: value});
		}).done();
	}

	async logout() {
		try{
			await AsyncStorage.removeItem("username");
			await AsyncStorage.removeItem("token");
			await AsyncStorage.setItem("loggedIn", false);
			this.setState({username:"", password:"", loggedIn:false});
		} catch (error){
			console.log(error.message);
		}
	}

userLogin = () => {
	let data = {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			username: this.state.username,
			password: this.state.password
		})
	}
	fetch(URL + "api/login", data)
		.then((response) => response.json())
		.then((responseData) => {
			console.log("TOKEN vvvv BELOW");
			console.log(responseData.token);
			var auth1 = "";
			if (responseData.token != undefined) {
				auth1 = responseData.token;
			}
			if (auth1.length > 10) {
				this.saveData("loggedIn", true);
				this.setState({
					loggedIn: true
				});
				this.saveData("token", responseData.token);
				this.saveData("username", this.state.username);
				Alert.alert(
					"Login Successfull!",
					"logged in as: " + this.state.username
				)
			} else {
				Alert.alert(
					"Login Failed",
					"Login Failed"
				)
			}
			})
		.done();
}

	render(){
		return(
			<View style={styles.container}>
			{!this.state.loggedIn && <View>
			<Text style={styles.inputHeader}>Username:</Text>
			<TextInput
			style={styles.inputField}
			onChangeText={(username) => this.setState({username})}
			value={this.state.username}/>

			<Text style={styles.inputHeader}>password</Text>
			<TextInput
			style={styles.inputField}
			secureTextEntry={true}
			onChangeText={(password) => this.setState({password})}
			value={this.state.password}/>
			<Button style={styles.button} onPress={() => this.userLogin()} title="Login"/>
			</View>
		}
		{this.state.loggedIn && <View>
			<Text style={styles.inputHeader}>Logged in as: {this.state.username}</Text>
			<Button title="logout" onPress={() => this.logout()}/>
			</View>
		}
			</View>
		)
	}
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		paddingTop: 22,
		alignItems: "center",
	},
	header: {
		padding: 10,
		fontSize: 20,
		height: 44,
	},
	inputHeader: {
		textAlign: "center",
		fontSize: 18,
		height: 30,
	},
	inputField: {
		alignItems: "center",
		height: 40,
		width: 300,
		borderColor: "black",
		borderWidth: 1,
		padding: 10,
	},
	button: {
		margin: 4,
		alignItems: 'center',
		backgroundColor: '#ff650c'
	},
	buttonText: {
		padding: 7,
		fontSize: 18,
		fontWeight: "bold",
		color: 'white'
	}
})

export default Login;