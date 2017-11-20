import React, {Component} from "react";
import placesData from "../facades/placesFacade";

class Places extends Component{
        
  constructor(){
      super();
      this.state = {data: "", err:""};
    }
    
  componentWillMount() {
      /*
      This will fetch data each time you navigate to this route
      If only required once, add "logic" to determine when data should be "refetched"
      */
      placesData.getData((e,data)=>{
        if(e){
          return this.setState({err:e.err});
        }
        this.setState({err:"",data});
      });
	}  
	

	mapData = (a) => {
		debugger
		var html = ""
		if(a === ""){
			return "";
		}
		var rows = a.map(function(p){
			return <tr><td>{p.city}</td><td>{p.zip}</td><td>{p.street}</td><td>{p.gpsLocation}</td><td>{p.description}</td><td>{p.rating}</td><td>{p.imgUri}</td></tr>;
		})
		return rows;
	}


  render() {
		var rows = this.mapData(this.state.data);
      return (
        <div>
          <h2>Places</h2>
          <p>This message is fetched from the server for anyone</p>
          <table className="table table-striped table-bordered table-hover">
		  <thead>
			  <tr><th>City</th><th>Zip</th><th>Street</th><th>Gps</th><th>Description</th><th>rating</th><th>imgUri</th></tr>
		  </thead>
		  <tbody>
			  {rows}
		  </tbody>		
		  </table>
          { this.state.err && ( 
            <div className="alert alert-danger errmsg-left" role="alert"> 
              {this.state.err}
            </div>
          )}
         
        </div>
      );
    }
}
export default Places;

