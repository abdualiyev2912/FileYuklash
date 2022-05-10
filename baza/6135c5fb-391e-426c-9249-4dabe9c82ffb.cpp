#include<iostream>
#include<math.h>
using namespace std;
int main(){
	double a1=1,a2=2,e;
	int k=3;
	cout<<"e=";
	cin>>e;
	do{
		if(k%2==1){
			a1=(a1+2*a2)/3;
		}
		else{
			a2=(a2+2*a1)/3;
		}
		k++;
		cout<<fabs(a1-a2)<<endl;
	}while(fabs(a1-a2)>e);
	
	cout<<"k="<<k;
	return 0;
}
