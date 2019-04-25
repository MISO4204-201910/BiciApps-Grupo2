$(document).ready(function(){
  $("#crearConfig").submit(function(e){
    e.preventDefault();
    var nombre =$("#valorConfig").val();
    var data ={'nombre':nombre};
    var url= BASE_URL + "crear/";
    var request= $.post(url,data);
    request.done(function(data){
        console.log(data);
    });
  });
});