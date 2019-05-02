clear all
close all
[wave_orig, fs] = audioread('audio_data/Vibracom_audio_05_10110010.wav'); %read the microphone data from the wav file


red = 12; %Since microphones sampling rate is extremely high, we can reduce it by a factor of 'red'
T=1/fs; %determining the time period
cyc_t = 0.05; %Number of seconds of vibration for each bit. Can calcualte bit rate by 1/cyc_t

len = 10*cyc_t; %determining the total time of relevant data (10bits of data)


j=1;
for i=1:red:length(wave_orig)  %reducing the sampling rate of the original data by 'red'
    wave(j) = wave_orig(i);
    time(j) = i*T;
    j=j+1;
end


plot(wave,'Linewidth',4); %plotting the extracted data
envelope(wave,5,'rms'); %Running the envelope function using rms values of a specific window
[yupper,ylower] = envelope(wave,5,'rms');%extracting the upper and lower envelope values into 2 vectors


for i=1:length(yupper)     %determining the time the relevant signal begins by probing for a spike
    if (yupper(i)>0.01)
        start_x = i;
        break;
    end
end

end_x = round(start_x+2*(fs*len/red)); %determing the time the signal ends by adding 10 bits worth of time to the start time


lp = lowpass(wave,0.08); %optional low pass filter for the wave file

k=1;
for i=start_x:end_x  %extracting relevant data the whole wave file
    rel_data(k)=yupper(i);
    k=k+1;
end
mean_val = mean(rel_data); 

for i=1:length(wave)  %extracting a clean square wave values from the upper envelope signal
    if (yupper(i)> mean_val)
        out(i) = 1;
    else
        out(i) = 0;
    end
end

figure
plot(out,'Linewidth',4); %plotting the square wave generated

j=1;
k=1;

for i=1:length(wave)-1   %extracting vector with rising edge times
    if (out(i)==0 && out(i+1)==1)
        re(j) = time(i+1);
        j=j+1;
    end

end


for i=1:length(wave)-1   %extracting vector with falling edge times
    if (out(i)==1 && out(i+1)==0)
        fe(k) = time(i-1);
        k=k+1;
    end

end


if length(re)>length(fe)   %removing any lone rising edge
    re(end)=[];
end

if length(re)<length(fe)   %removing any lone falling edge
    fe(1)=[];
end


one_times = fe-re; % extracting a vector with amount of time we have '1's
zero_times = re(2:end)-fe(1:end-1);% extracting a vector with amount of time we have '0's

zero_times(end+1)= time(end)-fe(end);

d=1;
for h=1:length(one_times) %extracting the code from the time period of '1's and '0's extracted
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





for i=1:length(code)  %detecting the preamble of '10' and extracting the following 8 bits.
    if (code(i)==1 && code(i+1)==0)
        mes = code(i+2:i+9);
        break;
    end
end

mes_dec = bi2de(mes,'left-msb');

disp(mes);    

