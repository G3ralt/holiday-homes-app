import React from "react";
import { Text, View, AsyncStorage, StyleSheet, TextInput, Button } from 'react-native';
const URL = require("../package.json").serverURL;

class Login extends React.Component{
	static navigationOptions = {title:"Log in"};

	constructor(){
		super();
		this.state = {data:"", err:"", username:"", password:""};
	}

	async _tokenSave (item, value) {
		try{
			await AsyncStorage.setItem(item, value);
		} catch (error) {
			console.log("Async storage: " + error.message);
		}
	}
	
	async _getToken(){
		try{
			await AsyncStorage.getItem("token");
		} catch (error){
			console.log("Async storage: " + error.message);
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
	fetch (URL + "api/login", data)
			.then((response) => response.json())
			.then((responseData) => {
				console.log("TOKEN vvvv BELOW")
				console.log(responseData.token);
				this._tokenSave("token", responseData.token)
			})
			.done();
}

	render(){
		return(
			<View style={styles.container}>
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
			<Button title="FUCKING TOKEN PLS" onPress={() => {console.log(this._getToken())}}/>
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