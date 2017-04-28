
$('a[data-toggle=layout-small-menu]').on('click', function(e){
	e.preventDefault(), e.stopPropagation(), $('.layout-fixed-header').toggleClass("layout-small-menu");
});

var jc = new $.jc();

function len(o){  
   var n, count = 0;  
   for(n in o){  
      if(o.hasOwnProperty(n)){  
         count++;  
      }  
   }  
   return count;
}  

$('.user-table .activeAccount').on('click', function(){
	var uid = $(this).attr("uid");
	$.post(BASE + '/admin/status', {uid:uid, type:'activeAccount'}, function(response){
		if(response){
			if(response.status == 200){
				window.location.reload();	
			} else{
				alertError(response.msg);
			}
		}
	});
});


$('.user-table .resendMail').on('click', function(){
	var uid = $(this).attr("uid");
	$.post(BASE + '/admin/status', {uid:uid, type:'resend'}, function(response){
		if(response){
			if(response.status == 200){
				alertOk('Email Delivered.');
			} else{
				alertError(response.msg);
			}
		}
	});
});

$('.user-table .disable').on('click', function(){
	var uid = $(this).attr("uid");
	$.post(BASE + '/admin/status', {uid:uid, type:'disable'}, function(response){
		if(response){
			if(response.status == 200){
				window.location.reload();	
			} else{
				jc.alertError(response.msg);
			}
		}
	});
});

$('.user-table .removeAdmin').on('click', function(){
	var uid = $(this).attr("uid");
	$.post(BASE + '/admin/status', {uid:uid, type:'removeAdmin'}, function(response){
		if(response){
			if(response.status == 200){
				window.location.reload();	
			} else{
                jc.alertError(response.msg);
			}
		}
	});
});


$('.user-table .setAdmin').on('click', function(){
	var uid = $(this).attr("uid");
	$.post(BASE + '/admin/status', {uid:uid, type:'setAdmin'}, function(response){
		if(response){
			if(response.status == 200){
				window.location.reload();	
			} else{
                jc.alertError(response.msg);
			}
		}
	});
});

$('.user-table .recoveryAccount').on('click', function(){
	var uid = $(this).attr("uid");
	$.post(BASE + '/admin/status', {uid:uid, type:'recoveryAccount'}, function(response){
		if(response){
			if(response.status == 200){
				window.location.reload();	
			} else{
                jc.alertError(response.msg);
			}
		}
	});
});

var setting_data = {};
$("#setting_form :input").change(function (){
	var key = $(this).attr('name');
	if(key == 'allow_signup'){
		setting_data[key] = $(this).is(':checked');
	} else{
		setting_data[key] = $(this).val();
	}
});


function save_settings(){
	if(len(setting_data) > 0){
		$.post(BASE + '/admin/settings', setting_data, function(response){
			setting_data = {};
			if(response){
				if(response.status == 200){
					jc.alertOk("Saved successful.");
				 } else {
                    jc.alertError(response.msg);
				 }
			}
		});
	}
	return false;
}

