package com.example.piratica;
//OKHTTP request from the API DO NOT NEED
public class LocalDeviceInfo {
//        OkHttpClient client = new OkHttpClient();
//        String url = "https://www.whoisxmlapi.com/whoisserver/DNSService?apiKey="+APIKey+"&domainName="+link+"&type=1&outputFormat=JSON";
//        try{
//            Request request = new Request.Builder()
//                    .url(url)
//                    .build();
//            try{
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        if(response.isSuccessful()){
//                            final String myResponse = response.body().string();
//                            JSONObject jsonObject = null;
//                            try {
//                                jsonObject = new JSONObject(myResponse);
//                                JSONObject dnsData = jsonObject.optJSONObject("DNSData");
//                                JSONArray dnsRecords = dnsData.getJSONArray("dnsRecords");
//                                JSONObject objects = dnsRecords.getJSONObject(0);
//                                Log.e("IP", objects.getString("address"));
//                                final ArrayList<String> IPList = new ArrayList<String>();
//                                for(int i=0;i<dnsRecords.length();i++){
//                                    JSONObject object = dnsRecords.getJSONObject(i);
//                                    if(object!=null){
//                                        String ip =object.getString("address");
//                                        IPList.add(ip);
//                                    }
//                                }
//                                information.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try
//                                        {
//                                            String netAddress = null;
//                                            netAddress = new NetTask().execute(link).get();
//                                            Boolean isChanged = false;
//                                            for(String item: IPList){
//                                                if(item.equals(netAddress)){
//                                                    isChanged=true;
//                                                    Log.e("IP", item);
//                                                }
//                                            }
//                                            if(netAddress!= null){
//                                                if(!isChanged){
//                                                    if(netAddress.startsWith("192.168")){
//                                                        header.setText("Hacker Detected");
//                                                        header.setTextColor(Color.parseColor("#EC4C33"));
//                                                        apiText.setText("\n IP address of website" + netAddress + "\n Actual Ip = " + IPList.get(0));
//                                                        networkState.setImageResource(R.drawable.alert);
//                                                        try {
//                                                            String data = new pingIP().execute(netAddress).get();
//                                                            if(data!= null){
//                                                                Hacker.setText("Hacker System Name "+data);
//                                                                Hacker.append("\n Hacker IP: "+netAddress);
//                                                            }
//                                                        } catch (ExecutionException e) {
//                                                        } catch (InterruptedException e) {
//                                                        }
//                                                    }
//                                                    else {
//                                                        header.setText("No Hacker Detected");
//                                                        header.setTextColor(Color.parseColor("#5CCB1C"));
//                                                        apiText.setText("\n DNS Record IP " + netAddress + "\n API Ip = " + IPList.get(0));
//                                                    }
//
//                                                }
//                                                else{
//                                                    header.setText("No Hacker Detected");
//                                                    header.setTextColor(Color.parseColor("#5CCB1C"));
//                                                    apiText.setText("\n Local IP "+netAddress+"\n API Ip = "+IPList.get(0));
//                                                }
//                                            }else{
//                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                                startActivity(intent);
//                                            }
//
//
//
//                                        }
//                                        catch (Exception e1)
//                                        {
//                                            e1.printStackTrace();
//                                        }
//
//                                    }
//                                });
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }
//                });
//
//            }catch(Exception e){
//                Toast.makeText(getApplicationContext(),"Unable to Access Website",Toast.LENGTH_SHORT).show();
//            }
//
//        }catch(Exception e){
//            Toast.makeText(getApplicationContext(),"Unable to Access Website",Toast.LENGTH_SHORT).show();
//        }
}
