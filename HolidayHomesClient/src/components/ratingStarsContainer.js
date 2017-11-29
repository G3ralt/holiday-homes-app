import React from 'react';
import StarRatingComponent from 'react-star-rating-component';
const URL = require("../../package.json").serverURL;

export default class Zvezdichka extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      rating: this.props.userRating,
      rating_empty_initial: this.props.userRating,
      editableRating: false,
      buttonText: 'Edit Rating',
      componentUsedOnPath: 'expectToBeChangedDinamically',
      userItself: { username: "unauthorized" },
      rentableName: 'isNotAplaceName',
      placeName: 'isNotArentableName',
      placeORrentable: 'willKnowInAwhile',
      placeORrentableName: 'hmmmm',
      userVoted: 'no'
    };
  }

  componentDidMount(){
    if(window.location.href.includes('places')){
      this.setState({ componentUsedOnPath: 'api/places/addRating', placeORrentableName: this.props.pName, placeORrentable: 'placeName', userItself: this.props.currentUser });
      this.checkFetchAndIfUserAlreadyVotedForPlaces();
    } else if(window.location.href.includes('rentables')) {
      this.setState({ componentUsedOnPath: 'api/rentables/addRating', placeORrentableName: this.props.rName, placeORrentable: 'rentableName', userItself: this.props.currentUser });
      this.checkFetchAndIfUserAlreadyVotedForRentables();
    }
  }

  onStarClick(nextValue, prevValue, name) {
    /*console.log('name: %s, nextValue: %s, prevValue: %s', name, nextValue, prevValue);*/
    this.setState({rating: nextValue});
  }

  checkFetchAndIfUserAlreadyVotedForPlaces() {
    if(this.props.userRating > 0){
      this.setState({ componentUsedOnPath: 'api/places/updateRating', userVoted: 'yes' });
    }
  }

  checkFetchAndIfUserAlreadyVotedForRentables() {
    if(this.props.userRating > 0){
      this.setState({ componentUsedOnPath: 'api/rentables/updateRating', userVoted: 'yes' });
    }
  }

  onEditButtonClick() {
    this.setState({editableRating: true, buttonText: 'Submit new Rating'});
    /*
    console.log('Window HREF = ', window.location.href);
    console.log('componentUsedOnPath = ', this.state.componentUsedOnPath);
    */
  }

  onSubmitNewRatingButtonClick() {
    const submitNewRatingObject = {
      [`${this.state.placeORrentable}`]: this.state.placeORrentableName,
      rating: this.state.rating,
      username: this.state.userItself.username
    }
    const options = {
      method: "POST",
      body: JSON.stringify(submitNewRatingObject),
      headers: { "Content-Type": "application/json" }
    }

    fetch(URL + this.state.componentUsedOnPath, options)
    .catch(err => {
        /*console.log(JSON.stringify(err));*/
    })
   
    this.setState({ editableRating: false, buttonText: 'Edit Rating' });
    /*console.log('New Rating Submitted with '+this.state.rating);*/
  }

  functionToCall= () => {
    if(this.state.buttonText === 'Edit Rating') {
    this.onEditButtonClick();
    } else if (this.state.buttonText === 'Submit new Rating'){
    this.onSubmitNewRatingButtonClick();
    }
  }

  render() {
    return (
      <div className="col-md-4 reactiveStars">
        <h4>Your Rating (from current user)</h4>
        {/* Default state = 'Non-Editable' */}
        {(this.state.userVoted !== 'yes') && (<p>(You haven't rated yet.)</p>)}
        <div style={{fontSize: 30}}>
          <StarRatingComponent
            name="cuzYouAreSkyFullOfStars"
            editing={this.state.editableRating}
            starCount={5}
            value={this.state.rating}
            onStarClick={this.onStarClick.bind(this)}
          />
        </div>
        <button 
        type="button"
        className="btn btn-light btn-sm"
        onClick={this.functionToCall}
        >{this.state.buttonText}
        </button>
        <br/>
      </div>
    );
  }
}