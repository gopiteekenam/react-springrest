import React, { Component } from "react";
import Modal from "react-responsive-modal";
import { feedname } from './Main.js';
import Main from './Main.js';
import '../react-progress-button.css';
import ProgressButton from 'react-progress-button';


export default class uploadRenderer extends Component {
  

    state = {
        open: false,
        button1State: '',
        loading: false
    };

    onOpenModal = () => {
        this.setState({ open: true });
    };

    onCloseModal = () => {
        this.setState({ open: false });
    };


    constructor(props) {
        super(props);
        this.state = {
            notes: '',
            comments: '',
            percentage:60,
        }
        this.handleUploadImage = this.handleUploadImage.bind(this);

    }

    onChange = (e) => {
        const state = this.state
        state[e.target.name] = e.target.value;
        this.setState(state);
    }
    
    handleUploadImage(ev) {
        
        ev.preventDefault();
        const data = new FormData();

        const {notes, comments } = this.state;

        var myfile = this.uploadInput.files[0];
        
     
        if (!myfile) {
            alert("No file selected.");
            console.log("No file selected.");
            this.setState({button1State: ''});
            return;
        }

		data.append('file', myfile);
        data.append('notes', this.state.notes);
        data.append('comments', this.state.comments);
        data.append('feedname', feedname);
        var localfeedname = feedname.replace(/\,/g,"").trim.toString;   
        
        var ccarstat = new String("CCAR_STAT_2000").trim.toString;
        var isEquelccar = (localfeedname === ccarstat) ? true : false;
        
        if(isEquelccar) {
        this.setState({button1State: this.setState({ loading: true})})
        fetch('/UX/uploadccarstat2000', {
            method: 'POST',
            body: data,
            
        })

        
        .then((resp)=>{ return resp.text() })
        .then((text)=>{ this.setState({button1State: ''});alert(text);})
        
         
    }
        var ccmisciratings = new String("CCMIS_CI_RATING");
        var localfeedname = feedname.replace(/\,/g,"").trim.toString;
        
        var isEquelccmisciratings = (localfeedname === ccmisciratings) ? true : false;
        
    if(isEquelccmisciratings) 
    {
        alert('inside isEquelccmisciratings...'+localfeedname)
        fetch('/UX/uploadccmiscirating', {
            method: 'POST',
            body: data,
        }).then(function (response) {
            if (response.status >= 200 && response.status < 300) {
                alert("File is successfully uploaded")
                return response.json
            }
            else {
                if(response.status == 400){
                    alert("Bad Request provide values in correct format")
                }
                alert("some thing happened wrong");
            }
        }
        )
    }
    }

    render() {
        const { open, notes, comments } = this.state;
        <Main ref={instance => { this.main = instance; }} />
        return (
               
         
            <div>
            
                <span><button style={{ height: 20, lineHeight: 0.5 }} onClick={this.onOpenModal} className="btn btn-info">Upload</button></span>
                 <div>
                   
                 </div>   
            <Modal open={open} onClose={this.onCloseModal} center>
                <h2>Upload popup window</h2>
                <form>
                    <div>Notes:     <input type="text" name="notes" placeholder="Enter notes here..." value={notes} onChange={this.onChange} /> <br />
                    </div>
                    <div>
                        Comments:    <input type="text" name="comments" placeholder="Enter comments here..." value={comments} onChange={this.onChange} /><br />
                    </div>
                    <div>
                        <input ref={(ref) => { this.uploadInput = ref; }} type="file" />
                    </div>
                    <br />
                      <div id='button-success'>
                    <ProgressButton state={this.state.button1State}  onClick={this.handleUploadImage}>Upload</ProgressButton>
                    </div>
                    <h2><button onClick={this.onCloseModal}>close</button>
                    </h2>
                </form>
            </Modal>
            </div>
        );
    }
};