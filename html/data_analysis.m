figure
close all
clear all


cyc_t = 3;
ax = 4;

data = csvread('Vibracom_log30.csv');
time = (data(:,1)-data(1,1))/(10^9);

lp = lowpass(data(:,ax),0.08);


data(:,ax) = data(:,ax)-min(data(:,ax));

plot(time,data(:,ax),'Linewidth',4);
envelope(data(:,ax),16,'rms')
[yupper,ylower] = envelope(data(:,ax),16,'rms');

figure
plot(lp,'Linewidth',4);



% temp = movmean(data(:,ax),50);


for i=1:length(data)
    if (yupper(i)> prctile(yupper,70))
        out(i) = 1;
    else
        out(i) = 0;
    end
end
% 
% for i=1:length(data)
%     if (temp(i)>mean(temp))
%         out2(i) = 1;
%     else
%         out2(i) = 0;
%     end
% end


figure
plot(out,'Linewidth',4);

% figure
% plot(out2,'Linewidth',4);

j=1;
k=1;

for i=1:length(data)-1
    if (out(i)==0 && out(i+1)==1)
        re(j) = time(i+1);
        j=j+1;
    end

end


for i=1:length(data)-1
    if (out(i)==1 && out(i+1)==0)
        fe(k) = time(i-1);
        k=k+1;
    end

end


if length(re)>length(fe)
    re(end)=[];
end

if length(re)<length(fe)
    fe(1)=[];
end


one_times = fe-re;
zero_times = re(2:end)-fe(1:end-1);

zero_times(end+1)= time(end)-fe(end);

d=1;
for h=1:length(one_times)
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









% 
% d=1;
% 
% for h=1:4
%     if (h<4)
%         xh = round((fe(h)-re(h))/3);
%         for f=d:d+xh
%             code(f)=1;
%             %d=d+1;
%         end
%         
%         d=d+xh;
%         
%         yh = round((re(h+1)-fe(h))/3);
%         for f=d:d+yh
%             code(f)=1;
%             d=d+1;
%         end
%         
%         d=d+yh;
%     
%     else 
%         zh = round((time(end)-fe(h))/3);
%         for f=d:d+zh
%             code(f)=1;
%             d=d+1;
%         end
%         
%         d=d+zh;
%     end
%             
% end


        
    



