#include <bits/stdc++.h>
using namespace std;

bool isFeasible(vector<int> v,long long mid, long long m){
    long long res=0;
    for(long long i=0;i<v.size();i++){
        if(v[i]>mid){
            res+=v[i]-mid;
        }
    }
    return res==m;
}

long long check(vector<int> v,long long mid) {
    long long res=0;
    for(long long i=0;i<v.size();i++){
        if(v[i]>mid){
            res+=v[i]-mid;
        }
    }
    return res;
}

int main() {
    long long n,m;
    cin>>n>>m;
    long long low=INT_MAX,high=INT_MIN;
    vector<int> v;
    for(long long i=0;i<n;i++){
        long long x;
        cin>>x;
        low=min(low,x);
        high=max(high,x);
        v.push_back(x);
    }
    long long ans = 0;
    while(high>=low){
        long long mid=(high+low)/2;
        if(check(v,mid) > m) {
            low = mid+1;
        } else if(check(v,mid) < m){
            high = mid-1;
        } else {
            ans = mid;
            low = mid+1;
        }
    }
    cout<<ans;
}