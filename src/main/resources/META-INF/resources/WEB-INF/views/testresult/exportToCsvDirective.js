// angular.module('mainApp').directive("exportToCsv", exportToCsv);
//
// function exportToCsv() {
//     return {
//         restrict: 'A',
//         controller: 'FunctionalCtrl',
//         scope: {},
//         link: function (scope, element, attrs) {
//             function stringify(str){
//                 return '"' +str.replace(/^\s\s*/, '').replace(/\s*\s$/,                       '').replace(/"/g, '""') +'"';
//             }
//             var el = element[0];
//             element.bind('click', function(e){
//                 var table = e.target.nextElementSibling;
//                 var csvString = '';
//                 for(var i=3; i<table.rows.length;i++){
//                     var rowData = table.rows[i].cells;
//                     for(var j=0; j<rowData.length;j++){
//                         if(rowData[j].innerHTML.indexOf('<a href')>-1){
//                             var ele = angular.element(rowData[j]);
//                             for(var k=0; k<ele[0].childNodes.length;k++){
//                                 if(ele[0].childNodes[k].tagName == 'A'){
//                                     csvString = csvString + stringify(ele[0].childNodes[k].innerText) + ",";
//                                 }
//                             }
//                         }else if(rowData[j].innerHTML.indexOf('<em')>-1 || rowData[j].innerHTML.indexOf('<strong>')>-1){
//                             var ele = angular.element(rowData[j]);
//                             csvString = csvString + stringify(ele[0].textContent) + ",";
//                         }else{
//                             csvString = csvString + stringify(rowData[j].innerHTML) + ",";
//                         }
//                     }
//                     csvString = csvString.substring(0,csvString.length - 1);
//                     csvString = csvString + "\n";
//                 }
//                 console.log(csvString);
//                 csvString = csvString.substring(0, csvString.length - 1);
//                 // alert(csvString);
//                 var a = $('<a/>', {
//                     style:'display:none',
//                     href:'data:application/csv;base64,'+btoa(csvString),
//                     download:'emailStatistics.csv'
//                 }).appendTo('body')
//                 a[0].click()
//                 a.remove();
//             });
//         }
//     }
// }