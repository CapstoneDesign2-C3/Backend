# Introduction
본 서비스는 관제사를 돕기 위한 **스마트 통합관제 시스템**으로
다중 카메라 환경에서 수집된 영상을 기반으로 **객체 탐지(Detection), 추적(Tracking), 재식별(Re-ID) 기술을 결합**하여 인물 단위 이동 경로를 파악하고 이벤트 발생 상황을 체계적으로 관리할 수 있도록 설계되었습니다.

이를 통해, 카메라·영상·객체·이벤트 정보를 유기적으로 연결하고 관제사가 보다 빠르고 정확하게 상황을 파악하며 효율적으로 대응할 수 있는 환경을 제공합니다.

## 기능
1. mobile_object 조회 ( 본 서비스에서는 사람을 의미합니다.)
   - 각 사람의 인상착의 및 feature, crop이미지, 등장 Video를 조회할 수 있습니다.
   - 이 사람의 이동경로 (Camera 1 -> Camera 2 -> ...) 를 조회할 수 있습니다.
2. event 조회
   - event의 발생 정보 및 위험도, 발생 당시 Video를 조회할 수 있습니다.
3. video 조회
   - video 조회와 함께 각 Video에 등장한 객체 정보를 함께 조회할 수 있습니다.
5. camera 조회
   - camera위치정보와 Camera에 담긴 Video 정보들을 조회할 수 있습니다.
6. report 작성
   - detected_object 정보와 event 정보를 통해 각각의 보고서를 작성할 수 있습니다.

## 시스템 구조도

<img width="1602" height="831" alt="image" src="https://github.com/user-attachments/assets/092ed3da-6484-4819-b214-3e3ccd1bdd75" />


# Backend API Spec
자세한 내용은 swagger를 확인하시길 바랍니다.

## 1. detected_object
   - **PUT /api/v1/detected-object/{detectedObjectId}** : detected Object의 별칭(alias)를 변경합니다.
   - **PUT /api/v1/detected-object/images/{detectedObjectId}**: detected Object의 crop Image를 변경합니다.
   - **GET /api/v1/detected-object** : filter 조건에 따라 detected Object의 정보를 Page 형식으로 조회합니다.
## 2. detection
   - **GET /api/v1/detection/tracks** : filter 조건에 따라 detection의 이동 상세 정보(track)를 Page 형식으로 조회합니다.
   - **GET /api/v1/detection/positions** : detectedObjectId가 이동 경로의 위치만 List 형식으로 조회합니다.
## 3. video
   - **GET /api/v1/video/{videoId}** : video와 video 촬영 위치, 그 video에 등장한 detectedObject의 정보를 조회합니다.
   - **GET /api/v1/video/mobile-detection** : detection Id를 통해 Video 정보와 detection의 상세 정보를 조회합니다.
   - **GET /api/v1/video/event** : event Id를 통해 Video 정보와 event의 상세 정보를 조회합니다.
## 4. camera
   - **GET /api/v1/camera** : filter 조건에 따라 Camera의 Position을 List 형식으로 조회합니다.
   - **GET /api/v1/camera/{cameraId}** : Camera에 담긴 Video 정보들을 List형식으로 조회합니다.
## 5. report
   - **POST /api/v1/create-mobile-object-track** : mobile Object Id 값들을 입력하면 id 하나당 하나의 보고서를 작성합니다.
   - **POST /api/v1/create-event** : filter 조건을 입력하면 이벤트 발생 목록 보고서를 작성합니다.
## 6. dashboard
   - **GET /api/v1/dashboard/time** : 날짜를 입력하여 시간에 따른 이벤트 발생량을 조회합니다.
   - **GET /api/v1/dashboard/risk** : 날짜를 입력하여 위험레벨에 따른 이벤트 발생량을 조회합니다.
   - **GET /api/v1/dashboard/event** : 날짜를 입력하여 이벤트 종류에 따른 발생량을 조회합니다.
   - **GET /api/v1/dashboard/camera** : 날짜를 입력하여 카메라별 이벤트 발생량을 조회합니다.
