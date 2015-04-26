x = [1110, 3560, 11080, 34696, 110205];

y20 = [0.46757, 0.36517, 0.24567, 0.15967, 0.10122];
y10 = [0.36486, 0.28090, 0.18439, 0.11719, 0.07390];
y1 = [0.18108, 0.10197, 0.063809, 0.04035, 0.02539];

z20 = [0.04836, 0.015366, 0.0049157, 0.0015674, 0.0004953];
z10 = [0.024478, 0.0077130, 0.0024609,  0.00078405, 0.0002476];
z1 = [0.0024752, 0.00077398, 0.00024636, 0.000078433, 0.000024772];

figure

semilogx(x, y20, '-r^', x, y10, '-ro', x, y1, '-rs', x, z20, '-g^', x, z10, '-go', x, z1, '-gs', 'LineSmoothing','on') %semilogx
grid on
xlabel('Num of Documents: Log Space')
ylabel('Auccuracy:  Log Space')
legend('LDA in Top 20','LDA in Top 10', 'LDA in Top 1', 'Baseline in Top 20', 'Baseline in Top 10', 'Baseline in Top 1')