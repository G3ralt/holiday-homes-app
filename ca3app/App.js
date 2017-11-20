import React from "react";
import { Text, View, Platform, TouchableOpacity, StyleSheet, Button, WebView, ScrollView } from 'react-native';
import { Constants, WebBrowser } from "expo";
import { StackNavigator } from 'react-navigation';
import Documentation from "./JS/Documentation";
import Places from "./JS/Places";


const Touchable = (props) => (
  <TouchableOpacity style={styles.button} onPress={props.onPress}>
    <Text style={styles.buttonText}>{props.title}</Text>
  </TouchableOpacity>)



class HomeScreen extends React.Component {
  static navigationOptions = { title: 'CA3 - Placr' };
  render() {
    const { navigate } = this.props.navigation;
    return (
      	<ScrollView >
	  	<Text style={{ textAlign: "center", fontSize: 20 }}>Placr™</Text>
      <Text style={{ textAlign: "center", fontSize: 20 }}>by Kasper Ravn Breindal</Text>
		  <Touchable onPress={() => navigate('documentation')} title="Documentation" />
      <Touchable onPress={() => navigate("places")} title="Places" />
      <Text style={{ textAlign: "center", fontSize: 16 }}>http://github.com/kasperonfire/CA3</Text>
      	</ScrollView>
    )
  }
}

export default App = () => <RouteStack style={{ marginTop: Platform.OS === 'ios' ? 0 : Constants.statusBarHeight / 2 }} />

const RouteStack = StackNavigator({
  Home: { screen: HomeScreen },
  documentation: { screen: Documentation },
  places: { screen: Places }
});

const styles = StyleSheet.create({
  button: {
    margin: 3,
    alignItems: 'center',
    backgroundColor: '#2196F3'
  },
  buttonText: {
    padding: 7,
    fontSize: 18,
    fontWeight: "bold",
    color: 'white'
  }
})