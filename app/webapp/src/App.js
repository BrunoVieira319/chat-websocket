import React from 'react';
import {InputText} from 'primereact/inputtext';
import {ScrollPanel} from 'primereact/scrollpanel';
import {Fieldset} from 'primereact/fieldset';
import {Dialog} from 'primereact/dialog';
import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.min.css';
import "primeflex/primeflex.css";

export default class App extends React.Component {

  constructor() {
    super()
    this.state = {
      nickName: "user",
      inputValue: "",
      messages: [],
      users: [],
      visible: true
    }
  }

  sendMessage = (event) => {
    if (event.key === 'Enter') {
      let message = {
        from: this.state.nickName,
        content: this.state.inputValue
      }

      this.socket.send(JSON.stringify(message))

      this.setState({
        inputValue: ""
      })
    }
  }

  connectToServer = (event) => {
    if (event.key === 'Enter' && this.state.inputValue.length >= 3) {
      this.socket = new WebSocket(`ws://localhost:8080/websocket/chat/${this.state.inputValue}`)

      this.socket.onopen = (response) => {
        this.setState({
          nickName: this.state.inputValue,
          inputValue: '',
          visible: false
        })
      }
  
      this.socket.onmessage = (response) => {
        let message = JSON.parse(response.data)
  
        if (message.type === "USERS_CONNECTED") {
          let users = [];
          Object.entries(message.data).forEach(element => {
            users.push(element[1])
          });

          this.setState({
            users: users
          })
          
        } else if (message.type === "MESSAGE") {
          let updateMessages = this.state.messages
          updateMessages.push(message.data)
          this.setState({
            messages: updateMessages,
          })
        }
       
      }
    }
  }

  render = () => (
    <div className="p-grid">
      <div className="p-col-2 p-offset-4" style={{border: "#c8c8c8 1px solid"}}>
        <ScrollPanel style={{width: '100%', height: '350px'}}>
          {this.state.messages.map((message, i) => (
            <Fieldset key={i} legend={<b>{message.from}</b>} >
              {message.content}
            </Fieldset>
          ))}
        </ScrollPanel>
        <InputText style={{width: '100%', marginTop: '10px'}}
          value={this.state.inputValue}
          onChange={(e) => this.setState({inputValue: e.target.value})}
          onKeyPress={this.sendMessage}
        />
      </div>
          
      <div className="p-col-2" style={{border: "#c8c8c8 1px solid", marginLeft: "10px"}}>
        <ScrollPanel style={{width: '100%', height: '350px'}}>
          {this.state.users.map((user, i) => (
            <Fieldset key={i}>
              {user}
            </Fieldset>
          ))}
        </ScrollPanel>
      </div>

      <Dialog header="Digite seu nick" 
        visible={this.state.visible} 
        style={{width: '200px'}} 
        modal={true} 
        onHide={(e) => this.setState({visible: false})} 
      >
          <InputText value={this.state.inputValue}
            onChange={(e) => this.setState({inputValue: e.target.value})}
            onKeyPress={this.connectToServer}/>
      </Dialog>
    </div>
  )
}

