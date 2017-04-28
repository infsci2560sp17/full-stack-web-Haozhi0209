
var jc = new $.jc();

function go_signin(){
	swal({
		title:"Information", 
		text:"You are not loggedin", 
		type:"warning",
		confirmButtonText:"Click to redirect",
		timer: 3000
	},function(isConfirm){
		if (isConfirm) {
			setTimeout(function(){
				window.location.href= BASE + '/signin';
			}, 300);
		} else{
			window.location.href= BASE + '/signin';
		}
	});
}

function len(o){  
   var n, count = 0;  
   for(n in o){  
      if(o.hasOwnProperty(n)){  
         count++;  
      }  
   }  
   return count;
}  


function openWindow(url,name,iWidth,iHeight) {  
    var url;                               
    var name;                              
    var iWidth;                         
    var iHeight;                           
    
    var iTop = (window.screen.availHeight-30-iHeight)/2;         

    var iLeft = (window.screen.availWidth-10-iWidth)/2;            
    window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');  
}  

function dispatch() {
    var q = document.getElementById("q");
    if (q.value != "") {
        var url = 'https://www.google.com/search?q=site:java-china.org/topic%20' + q.value;
        if (navigator.userAgent.indexOf('iPad') > -1 || navigator.userAgent.indexOf('iPod') > -1 || navigator.userAgent.indexOf('iPhone') > -1) {
            location.href = url;
        } else {
            window.open(url, "_blank");
        }
        return false;
    } else {
        return false;
    }
}

function emoji(content){
	if(content && content.indexOf(':') != -1){
		return content.replace(/:([a-z-_]{2,30}):/g, "<img src='"+ CDN_URL + "/assets/emojis/$1.png'  height='20' width='20' />");
	}
	return content;
}

function change_captcha(){
	var timestamp = (new Date()).valueOf();  
	$('#captcha').attr('src', BASE + '/captcha?t=' + timestamp);
	return false;
}


$('#topic-add .preview').on('click', function(){
	var content = $("#topic-add #content").val();
	if(content){
        jc.post({
            url : BASE+'/markdown',
            data: {content : content},
            success: function (result) {
                if(result && result.success){
                    $("#markdown_preview").html(result.payload).removeClass('hide');
                    $('#markdown_preview pre code').each(function(i, block) {
                        hljs.highlightBlock(block);
                    });
				}
            }
        });
	} else{
		$("#markdown_preview").html('').addClass('hide');
	}
});

$('#topic-edit .preview').on('click', function(){
	var content = $("#topic-edit #content").val();
	if(content){
		$.post(BASE + '/markdown', {content : content}, function(response){
			if(response){
				$("#markdown_preview").html(response).removeClass('hide');
				$('#markdown_preview pre code').each(function(i, block) {
				    hljs.highlightBlock(block);
				});
			}
		});
	} else{
		$("#markdown_preview").html('').addClass('hide');
	}
});

$('.topic-footer .heart').on('click', function(){
	var _this = $(this);
	var A = $(this).attr("rel");
	var C=parseInt($("#likeCount").text());
	var D = $("#likeCount");
	$(this).css("background-position","")
	if(A == 'like'){
		D.text(C+1);
		_this.addClass("heartAnimation").attr("rel","unlike");
	} else{
		D.text(C-1);
		_this.removeClass("heartAnimation").attr("rel","like");
		_this.css("background-position","left");
	}
	var tid = $(this).attr('tid');
	$.post(BASE + '/favorite', {type:'love',event_id : tid}, function(response){
		if(response){
			if(response.status == 200){
			} else if(response.status == 401){
				go_signin();
			} else{
				alertError(response.msg);
			}
		}
	});
});

$('.topic-detail-heading .sinks').on('click', function(){
	var tid = $(this).attr("tid");
    jc.post({
        url : BASE+'/sink',
        data: {tid : tid},
        success: function (result) {
            if(result && result.success){
                window.location.reload();
            } else {
                if(result.code == 401){
                    go_signin();
                } else {
                    jc.alertError(result.msg || 'Topic sink failed');
                }
            }
        }
    });
});


$('.topic-footer .deltopic').on('click', function(){
	var tid = $(this).attr("tid");
    jc.post({
        url : BASE+'/delete',
        data: {tid : tid},
        success: function (result) {
            if(result && result.success){
                window.location.href = "/";
            } else {
                if(result.code == 401){
                    go_signin();
                } else {
                    jc.alertError(result.msg || 'Set essence failed.');
                }
            }
        }
    });
    return false;
});

$('.profile .following').on('click', function(){
	var uid = $(this).attr("uid");
    jc.post({
        url : BASE+'/favorite',
        data: {type:'following', event_id : uid},
        success: function (result) {
            if(result && result.success){
                window.location.reload();
            } else {
                if(result.code == 401){
                    go_signin();
                } else {
                    jc.alertError(result.msg || 'Topic publish failed');
                }
            }
        }
    });
    return false;
});

$("#comment-form .ladda-button").on('click', function(e){
	e.preventDefault();
	var form = $('#comment-form')[0];
	if(form.checkValidity()) {
		var formData = $('#comment-form').serialize();
        jc.post({
            url : BASE+'/comment/add',
            data: formData,
            success: function (result) {
                $('#comment-form')[0].reset();
                $('#comment-form button').removeAttr('disabled');
                if(result && result.success){
                    window.location.reload();
                } else {
                    if(result.code == 401){
                        go_signin();
                    } else {
                        jc.alertError(result.msg || 'Reply failed');
                    }
                }
            }
        });
	}
	return true;
});



var topic = {};

topic.edit = function(){
	var formData = $('#topic-edit').serialize();
    jc.post({
        url : BASE+'/topic/edit',
        data: formData,
        success: function (result) {
            if(result && result.success){
                window.location.href = BASE + '/topic/' + result.payload;
            } else {
                if(result.code == 401){
                    go_signin();
                } else {
                    jc.alertError(result.msg || '密码修改失败');
                }
            }
        }
    });
	return false;
};

var user = {};

user.update_avatar = function(){
	var avatar = $("#avatar_form #user_avatar").val();
	if(avatar && avatar != ''){
		jc.post({
			url : BASE+'/settings',
			data: {type:'avatar', avatar : avatar},
			success: function (result) {
				if(result && result.success){
                    jc.alertOkAndReload('Avatar change successful');
				} else {
					if(result.code == 401){
                        go_signin();
					} else {
						jc.alertError(result.msg || 'Avatar change failed');
					}
				}
            }
		});
	}
	return false;
};


