import React, {Component} from "react";
import { feedname } from './Main.js';
import { CSVLink, CSVDownload } from "react-csv";

export default class ChildMessageRenderer extends Component {
     csvLink = React.createRef()
    state = {
        data: ''
        
        
     };
    
         constructor(props) {
        super(props);
        
        this.downloadTemplate = this.downloadTemplate.bind(this);
    }

    downloadTemplate(ev) {
    	
        const API ='/UX/downloadcsv?feedname=';
        const query = feedname;
        alert('feedname'+feedname);
        const localfeedname=feedname;
        alert("local feedname:"+localfeedname);
       fetch(API+query).then(response => 
    	response.text()
    	.then(data => {
    				  console.log("feedname in data:"+feedname);
    				  if(data === '' ||localfeedname !== '' && localfeedname !== feedname){
    				  alert("inside data is empty");
    				  	this.downloadTemplate();
    				  }
    				  else{
    				  this.setState({ data }, () => {
    				  if (navigator.msSaveBlob) {
    				  alert("feedname in ie"+feedname);
      					let blob = new Blob([data], {
        				"type": "text/csv;charset=utf8;"
      					});
      					navigator.msSaveBlob(blob, this.fileName);
    					}
    				else {
    				alert("feedname in other than ie..."+feedname);
    				  this.link.href = `data:text/csv,${encodeURI(data)}`
                      this.link.download = feedname+`.csv`
                      this.link.click()
                      }     	
                      
       })
       
       }
		
		}));
		
		
    }



    render() {
        return (
            <span>
                <div>
                 <a ref={link => this.link = link}
        style={{display: 'none'}}
      />   
                <button style={{height: 20, lineHeight: 0.5}} onClick={this.downloadTemplate} className="btn btn-info">Download</button>
                </div>
                </span>
        );
    }
};