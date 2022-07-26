#include<bits/stdc++.h>
#include<winsock2.h>
#pragma  comment(lib,"libws2_32.a")

using namespace std;
unordered_map<string,string>mp;
int check(string ID,string tp)
{
    int k=0;
    string tp_id=tp.substr(0,ID.length());
    if(tp[ID.length()]=='/')
    {
        if(tp_id==ID)
        {
            return 1;
        }
    }
    return -1;
}
int main(int argc , char *argv[])
{
     WSADATA wsa;
     struct sockaddr_in server,client;
        if (WSAStartup(MAKEWORD(2,2),&wsa) != 0)
	{
		printf("Failed. Error Code : %d",WSAGetLastError());
		return 1;
    }
    SOCKET s,client_sock;
    if((s = socket(AF_INET , SOCK_STREAM,0)) == INVALID_SOCKET)
	{
		printf("Could not create socket : %d" , WSAGetLastError());
	}
     server.sin_addr.s_addr =INADDR_ANY;
	 server.sin_family = AF_INET;
	 server.sin_port = htons(49199);
  
    if (bind(s,(struct sockaddr *)&server , sizeof(server)) < 0)
	{
	    cout<<"error in binding"<<endl;
		
		return 1;
	}
    if(listen(s,SOMAXCONN)<0)
    {
        cout<<"error in listening"<<endl;
        return 1;
    }
    int len=0;
    cout<<"server is listening ...";
    
        int client_size=sizeof(client);
        while(1)
        {
            string forw;
            cout<<"server is listening ..."<<endl;
        client_sock=accept(s,(struct sockaddr *)&client,&client_size);

          // printf("server accept the client...\n");
           char buffer[1024];
           while(1)
           {
              int valread = recv(client_sock,buffer,1025,0);
             // cout<<valread<<endl;
             // int i=0;
             string ID;
             string pass;
             int count=0;
             string email;
             string no;
              for(int i=2;i<valread;i++)
              {
                 // cout<<buffer[i];
                 if(buffer[i]=='/')
                 {
                    count++;
                    //swi=0;
                 }
                 else if(count==0)
                  ID=ID+buffer[i];
                  else if(count==1)
                  pass=pass+buffer[i];   
                  else if (count==2)
                  email=email+buffer[i];
                  else if (count==3)
                  no=no+buffer[i];
              }
              cout<<"id is"<<ID<<endl;
              cout<<"pass is"<<pass<<endl;
              cout<<"email is "<<email<<endl;
              cout<<"no id "<<no<<endl;
              // login
              if(email.length()==0)
              {
              
                        if(mp.find(ID)==mp.end())
                       {
                           // please register
                             forw="-1";
                          send(client_sock,forw.c_str(),forw.size()+1,1);
                       }
                         else if(mp[ID]==pass)
                     {
                         // match
                        forw="1";
                        fstream f;
                        char ch;
                      f.open("data.txt",ios::in);
                          if (f.is_open()){   //checking whether the file is open
                          string tp;
                          while(getline(f,tp)){ //read data from file object and put it into string
                          if(check(ID,tp)==1)
                          {
                              forw=tp;
                              break;
                          }
                        }
                      }
                         cout<<forw<<endl;
                        int sendRESULT=send(client_sock,forw.c_str(),forw.size()+1,1);
                     }
                     else
                     {   
                         //incorect password
                         forw="2";
                          int sendRESULT=send(client_sock,forw.c_str(),forw.size()+1,1);

                     }
              }
              else 
              {
                  if(mp.find(ID)!=mp.end())
                  {
                      // user id exits
                        forw="-1";
                      send(client_sock,forw.c_str(),forw.size()+1,1);
                  }
                  else 
                  {
                      // registration succesfull
                      mp[ID]=pass;
                      fstream f;
                      f.open("data.txt",ios::app);
                      if (!f) {
                          	cout << "File not created!";
                              }
	              else {
                      cout << "File created successfully!";
                      f<<ID+'/'+pass+'/'+email+'/'+no+'/'+'\n';
                      f.close(); 
                      }
                       forw="1";
                      send(client_sock,forw.c_str(),forw.size()+1,1);
                  }
              }               
           // cout<<sendRESULT<<endl;
            break;
           }
        }
              
}
