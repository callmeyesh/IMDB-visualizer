var starJsonName = 'JSON/2010.json';
var mapObject;

$(function () {
  getData(starJsonName);
  ticker();
})

function getData(starJsonName) {
    return $.getJSON(jsonName, function(data){
      $('#world-map').vectorMap({
        map: 'world_mill_en',
        scaleColors: ['#C8EEFF', '#0071A4'],
        normalizeFunction: 'polynomial',
        hoverOpacity: 0.7,
        hoverColor: false,
        markerStyle: {
          initial: {
            fill: '#0C69F3',
            stroke: false,
            r: 2
          }
        },
        regionStyle: {
          initial: {
          fill: '#453822',
          stroke: false,
        }
        },
        backgroundColor: false,
        markers: data,    
          onMarkerLabelShow: function(event, label, index){
            label.html(
              '<b>'+data[index].name+'</b><br/>'
            );
          },
        });
      mapObject = $('#world-map').vectorMap('get', 'mapObject');
    });
}

function startDisplay(jsonName) {
   $.getJSON(jsonName, function(data){
      $('#world-map').vectorMap({
        map: 'world_mill_en',
        scaleColors: ['#C8EEFF', '#0071A4'],
        normalizeFunction: 'polynomial',
        hoverOpacity: 0.7,
        hoverColor: false,
        markerStyle: {
          initial: {
            fill: '#0C69F3',
            stroke: false,
            r: 2
          }
        },
        regionStyle: {
          initial: {
          fill: '#453822',
          stroke: false,
        }
        },
        backgroundColor: false,
        markers: data,    
          onMarkerLabelShow: function(event, label, index){
            label.html(
              '<b>'+data[index].name+'</b><br/>'
            );
          },
        });
      mapObject = $('#world-map').vectorMap('get', 'mapObject');
    });

   
   //console.log(mapObject);
   //console.log('Test');
   //mapObject.removeAllMarkers();
}

$('#target').ready(function() {
    getData('content').always(function() { //is returned as deffered object
        console.log($('#content').data('key')); //runs when ajax completed
    });
});​

function ticker(){
  console.log("ticker");
  console.log(mapObject);
  //mapObject.removeAllMarkers();
}

