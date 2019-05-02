figure
close all
clear all


cyc_t = 2; %Number of seconds of vibration for each bit. Can calcualte bit rate by 1/cyc_t
ax = 4; %Selecting the axis to analyze. time=1, x=2, y=3, z=4

data = csvread('accel_data/Vibracom_log_2s_10101010.csv'); %read the accelerometer data from the csv file

time = (data(:,1)-data(1,1))/(10^9);

lp = lowpass(data(:,ax),0.08); %option step to filter with a Low pass filter


data(:,ax) = data(:,ax)-min(data(:,ax)); %gravity removal

plot(time,data(:,ax),'Linewidth',4); %plotting the z axis accelerometer readings
envelope(data(:,ax),15,'rms') %Running the envelope function using rms values of a specific window
[yupper,ylower] = envelope(data(:,ax),15,'rms'); %extracting the upper and lower envelope values into 2 vectors

figure
plot(lp,'Linewidth',4); %plotting the low pass filtered version of the accelerometer data


for i=1:length(data)     %extracting a clean square wave values from the upper envelope signal
    if (yupper(i)> prctile(yupper,50))
        out(i) = 1;
    else
        out(i) = 0;
    end
end

figure
plot(out,'Linewidth',4); %plotting the square wave generated

j=1;
k=1;

for i=1:length(data)-1      %extracting vector with rising edge times
    if (out(i)==0 && out(i+1)==1)
        re(j) = time(i+1);
        j=j+1;
    end

end


for i=1:length(data)-1     %extracting vector with falling edge times
    if (out(i)==1 && out(i+1)==0)
        fe(k) = time(i-1);
        k=k+1;
    end

end


if length(re)>length(fe) %removing any lone rising edge 
    re(end)=[];
end

if length(re)<length(fe)%removing any lone falling edge 
    fe(1)=[];
end


one_times = fe-re; % extracting a vector with amount of time we have '1's
zero_times = re(2:end)-fe(1:end-1); % extracting a vector with amount of time we have '0's
zero_times(end+1)= time(end)-fe(end);

d=1;
for h=1:length(one_times)  %extracting the code from the time period of '1's and '0's extracted
    xh = round(one_times(h)/cyc_t);
    yh = round(zero_times(h)/cyc_t);
    for f=1:xh
          code(d)=1;
          d=d+1;
    end
    
    for f=1:yh
          code(d)=0;
          d=d+1;
    end
end

figure
plot(data(:,ax),'Linewidth',4);



for i=1:length(code)                     %detecting the preamble of '10' and extracting the following 8 bits.
    if (code(i)==1 && code(i+1)==0)
        mes = code(i+2:i+9);
        break;
    end
end

mes_dec = bi2de(mes,'left-msb');

disp(mes);    



